package com.onlinetea.prower.TestController.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlinetea.prower.TestController.Params;
import com.onlinetea.prower.Uitls.JSONUtils;
import com.onlinetea.prower.constants.Constants;
import com.onlinetea.prower.mapper.ResourceMappingMapper;
import com.onlinetea.prower.mapper.dto.ResourceMappingPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import java.util.*;
import java.util.stream.Collectors;
import static com.alibaba.fastjson.JSON.parseObject;

@Slf4j
@Service
public class UyunServiceImpl implements UyunService{

    @Autowired
    private ResourceMappingMapper mappingMapper;

    @Override
    public JSONObject dealData(Map<String,JSONObject> itemI6000,String cTypeId) {
        // 定义返回信息  code=T10102*/
        JSONObject respone = new JSONObject();
        QueryWrapper<ResourceMappingPO> wrapper = new QueryWrapper<>();

        wrapper.eq("mapping_resource", cTypeId);

        //haveMapping，以数据源进行切分*/
        List<ResourceMappingPO> haveMapping = mappingMapper.selectList(wrapper);
        String cmdbD = getCmdbData();
        // 根据模型ID查询对应的属性，如果有，才开始同步 */
        if(haveMapping!=null&&!haveMapping.isEmpty()){
        // 获得classCode*/
        String classCode = haveMapping.get(0).getResource();
        // 查询I6000接口时，需要的属性参数*/
        String I6fileds = haveMapping.stream().map(ResourceMappingPO::getMappingField).
                collect(Collectors.joining(","));
        // 组建I6000和CMDB的映射MAP*/
        Map<String,String> resourceMap = haveMapping.stream().
                collect(Collectors.toMap(ResourceMappingPO::getMappingField,ResourceMappingPO::getField));

//        Map<String,JSONObject> itemI6000 = getI6000Resource(code,I6fileds,respone);
//
//        String cmdbD = getCmdbData();
        JSONObject resultCMDBAll = parseObject(cmdbD);
        String empty = resultCMDBAll.getString("empty");
        JSONArray cmdbData = resultCMDBAll.getJSONArray("dataList");
        // CMDB资源数据集 key:c_pxzid  value:具体资源数据
        Map<String,JSONObject> itemCMDB = new HashMap<>(32);
        // cmdb的接口返回值，若为false，代表datalist有数据*/
        if(StringUtils.equals("false",empty)){
            for(Object object : cmdbData){
                JSONObject data  = (JSONObject)object;
                itemCMDB.put(data.get("cq_pzxid").toString(),data);
            }
        }
        // 待新建数据*/
        Map<String,JSONObject> toBeCreate = new HashMap<>();

        // 待更新数据*/
        List<Map<String,String>> beUpdata = new ArrayList<>();

        // 更新变量
        Map<String,String> updataData;

        // I6000资源数据 itemI6000、CMDB的资源数据 itemCMDB，循环I6000数据*/
        for(Map.Entry<String,JSONObject> entry:itemI6000.entrySet()){

            // dataCM是CMDB中，对应CI_ID的资源数据 */
            JSONObject dataCM =  itemCMDB.get(entry.getKey());
            if(dataCM == null){
                // I6000中，新增的资源*/
                toBeCreate.put(entry.getKey(),entry.getValue());
                continue;
            }
            // 比较一组I6000和CMDB的资源数据 */
            updataData = comparingCmdb(entry.getValue(),dataCM,resourceMap);
            updataData.put("classCode",classCode);
            updataData.put("id",dataCM.get("id").toString());
            beUpdata.add(updataData);
            //下面调用更新接口
        }
            respone.put("beUpdata", beUpdata);
            List<Map<String,String>> beCreate = new ArrayList<>();
            Map<String,String> letData;
            for(Map.Entry<String,JSONObject> entry:toBeCreate.entrySet()){
                letData = traToCmdbData(entry.getValue(),resourceMap);
                letData.put("classCode",classCode);
                beCreate.add(letData);
            }
            respone.put("beCreate", beCreate);
        }else{
            respone.put("result","failed");
            respone.put("message","CMDB中不存在该模型ID"+cTypeId);
        }
        return respone;
    }


    private Map<String,JSONObject> getI6000Resource(String code,String I6fileds,JSONObject respone){
        /** 构造I6000的对比数据，CI_ID为key，具体数据为value*/
        Map<String,JSONObject> itemI6000 = new HashMap<>(32);
        // 构造查询条件
        JSONObject requestBody = new JSONObject();
        /** 拼接I6000请求参数*/
        requestBody.put("app_code", "CMDB");
        requestBody.put("userId", "6a10f5a44c30b5c1013c36e214140fd2");
        requestBody.put("RUN_CORP_CODE", "0126F97C122D074AB44A02E018E6DF5A");
        requestBody.put("attrCode",I6fileds);
        requestBody.put("pageSize", "100");

        /** 模拟I6000返回的数据*/
        String resultI6000 = Constants.I6OOODATA;
        /** 解析I6000返回的JSON数据*/
        JSONObject resultObjectAll = parseObject(resultI6000);
        JSONObject resultValueAll = resultObjectAll.getJSONObject("resultValue");
        /** 解析json，获得items的值*/
        JSONArray I6000ITS = resultValueAll.getJSONArray("items");

        /** I6000数据判空*/
        if(I6000ITS==null||CollectionUtils.isEmpty(I6000ITS)){
            respone.put("result","failed");
            respone.put("message","I6000中该模型ID: "+code+"不存在资源值");
        }

        for(Object object : I6000ITS){
            JSONObject data  = (JSONObject)object;
            itemI6000.put(data.get("CI_ID").toString(),data);
        }
        return itemI6000;
    }

    /**
     * 根据CI_ID找到两边一致的资源数据
     * 一个一个比对
     * @param comparingV I6000和CMDB的映射关系,key是I6000的属性，value是CMDB的属性
     * @param I6000 I6000的值
     * @param cmdb cmdb的值
     * @return updataData 待更新的属性和值
     * */
    private Map<String,String> comparingCmdb(JSONObject I6000,JSONObject cmdb,Map<String,String> comparingV){
        //返回需要更新的属性和值*/
        Map<String,String> updataData = new HashMap<>(64);
        List<String> toBeNull = new ArrayList<>();
        // 只比较需要更新的属性 */
        for(Map.Entry<String,String> entry:comparingV.entrySet()){
            // I6000中，没有找到的属性，暂时记录下来*/
           if(I6000.get(entry.getKey())==null){
               toBeNull.add(entry.getKey());
               continue;
           }
            // 比较I6000和CMDB相同属性的值是否一样，不一样的，就是待更新属性*/
           if(!StringUtils.equals(I6000.get(entry.getKey()).toString().trim(),
                   cmdb.get(entry.getValue()).toString().trim())){
               updataData.put(entry.getValue(),I6000.get(entry.getKey()).toString());
             }
        }
        return updataData;
    }

    // 模拟CMDB的数据   /** 调用CMDB接口，"store/openapi/v2/resources/query?apikey=&source="*/
    private String getCmdbData(){
        String cmdbData = Constants.CMDBDATA;
        Map<String, String> headers = new HashMap<>();
        Params params = new Params();
        params.setField("classCode");
        params.setValue("BlandService");
        List<Params> paramsList = new ArrayList<Params>();
        paramsList.add(params);
        JSONObject requestBody = new JSONObject();
        requestBody.put("pageNum", "100");
        Map<String,JSONObject> itemCMDB = new HashMap<>(32);
        // 用于构造cmdb接口查询条件
        requestBody.put("conditions", paramsList);
        int pageNum = 0;
        int size;

        requestBody.put("pageNum",pageNum );
        queryCmdbByField(requestBody);

        return  cmdbData;
    }


    /**
     * 将I6000属性转换成CMDB的属性
     *
     * @param I6000Data I6000数据
     * @param resourceMap 映射数据
     *
     * @return prepare CMDB的属性map
     * */
    private Map<String,String> traToCmdbData(JSONObject I6000Data,Map<String, String> resourceMap){
        Map<String,String> prepare = new HashMap<>();
        List<String> toBeNull = new ArrayList<>();
        for(Map.Entry<String,String> resEntry : resourceMap.entrySet()){
            // resourceMap，key是I6000属性，value是CMDB属性
            if(I6000Data.get(resEntry.getKey())==null){
                toBeNull.add(resEntry.getKey());
                continue;
            }
            // 转成CMDB属性的数据集
            prepare.put(resEntry.getValue(),I6000Data.get(resEntry.getKey()).toString());
        }
        return prepare;
    }

    /**
     * 查询cmdb一个模型下，所有的资源信息
     *
     * @param requestBody 请求体
     * @return 系统信息列表
     */
    public String queryCmdbByField(JSONObject requestBody) {
        Map<String, String> headers = new HashMap<>();

        try {
            // ① 封装url
            String url = "localhost:8015/vi/test"
                    + "?apikey=1012&source=cmdb";
            // 请求头
            headers.put("Content-Type", "application/json");
            log.info("---queryCmdbInfoByField--查询cmdb获取信息接口url:{},body:{}", url, JSONUtils.toJSON(requestBody));
            // 查询



            return null;
        } catch (Exception e) {
            log.error("--queryCmdbInfoByField--查询cmdb获取信息接口异常,查询条件: requestBody:{}", JSONUtils.toJSON(requestBody), e);
            return null;
        }
    }
}
