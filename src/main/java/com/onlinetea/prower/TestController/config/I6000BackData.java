package com.onlinetea.prower.TestController.config;

public class I6000BackData {

//package com.nari.resourcemgr.resourcemgr.serviceapi;
//
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.nari.resourcemgr.resourcemgr.business.dto.Params;
//import com.nari.resourcemgr.resourcemgr.common.constants.Constants;
//import com.nari.resourcemgr.resourcemgr.component.DataFusionValues;
//import com.nari.resourcemgr.resourcemgr.component.I6000ApiComponent;
//import com.nari.resourcemgr.resourcemgr.component.UyunApiComponent;
//import com.nari.resourcemgr.resourcemgr.mapper.ResourceMappingMapper;
//import com.nari.resourcemgr.resourcemgr.mapper.po.ResourceMappingPO;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.*;
//import java.util.stream.Collectors;
//import static com.alibaba.fastjson.JSON.parseObject;
//
//    @Slf4j
//    @Service
//    public class dealI6ServiceImpl implements dealI6Service{
//        @Autowired
//        private DataFusionValues dataFusionValues;
//
//        @Autowired
//        private ResourceMappingMapper mappingMapper;
//
//        @Autowired
//        private I6000ApiComponent i6000Api;
//
//        @Autowired
//        private UyunApiComponent uyunApi;
//
//        @Override
//        public JSONObject dealResource(String ciTypeId,JSONObject I6000Data) {
//            // 定义返回信息
//            JSONObject respone = new JSONObject();
//            QueryWrapper<ResourceMappingPO> wrapper = new QueryWrapper<>();
//            if(StringUtils.isBlank(ciTypeId)){
//                respone.put("message","I6000通过kafka传入的ciTypeId为空，无法执行");
//                log.error("I6000通过kafka传入的ciTypeId为空，无法执行");
//                return respone;
//            }
//            wrapper.eq("mapping_resource", ciTypeId);
//            List<ResourceMappingPO> haveMapping = mappingMapper.selectList(wrapper);
//            // 根据模型ID查询对应的属性
//            if (haveMapping != null && !haveMapping.isEmpty()) {
//                // 获得classCode
//                String classCode = haveMapping.get(0).getResource();
//                // 查询I6000接口时，需要的属性
////          String fileds =
////              haveMapping.stream()
////                  .map(ResourceMappingPO::getMappingField)
////                  .collect(Collectors.joining(","));
//                // 组建I6000和CMDB的映射MAP
//                Map<String, String> resourceMap =
//                        haveMapping.stream()
//                                .collect(
//                                        Collectors.toMap(
//                                                ResourceMappingPO::getMappingField, ResourceMappingPO::getField));
//                // 调用I6000接口，获得对应模型的资源数据，CI_ID为key
////            Map<String,JSONObject> itemI6000 = getI6000Resource(ciTypeId,fileds);
////            log.info("获得I6000数据，{}",itemI6000);
//                // 调用CMDB接口，获得对应模型的资源数据，c_pxzid为key
//                Map<String,JSONObject> itemCMDB = getCmdbResource(classCode);
//                log.info("获得CMDB数据，{}",itemCMDB);
//                // 待新建数据
//                List<Map<String,String>> toBeCreate = new ArrayList<>();
//                // 待更新数据
//                List<Map<String,String>> toBeUpdata = new ArrayList<>();
//                // 临时更新变量
//                Map<String,String> updataData;
//                // 临时新增变量
//                Map<String,String> createData;
//                // I6000资源数据——itemI6000  CMDB的资源数据——itemCMDB
////            for(Map.Entry<String,JSONObject> entry:itemI6000.entrySet()){
//                // 比较CI_IDh和c_pxzid,因为I6000推送的消息只会是单个的，所以不用遍历了
//                JSONObject dataCM =  itemCMDB.get(I6000Data.getString("CI_ID"));
//                if(dataCM == null){
//                    // I6000中，需要新增的资源，先转成CMDB资源格式
//                    createData = traToCmdbData(I6000Data,resourceMap,classCode);
//                    toBeCreate.add(createData);
//
//                }
//                // 比较一组I6000和CMDB的资源数据，并转换成CMDB的数据格式
//                updataData = comparingCmdb(I6000Data,dataCM,resourceMap,classCode,dataCM.get("id").toString());
//                toBeUpdata.add(updataData);
////            }
//
//                // 调用cmdb批量新增接口
//                if(!toBeCreate.isEmpty()){
//                    uyunApi.createCmdbResource(toBeCreate);
//                }
//                if(!toBeUpdata.isEmpty()){
//                    uyunApi.createCmdbResource(toBeUpdata);
//                }
//                // 调用cmdb批量更新接口
//
//                respone.put("status","success");
//                respone.put("更新操作信息","更新了"+toBeUpdata.size()+"数据");
//                respone.put("新增操作信息","新增了"+toBeCreate.size()+"数据");
//                return respone;
//            }else{
//                respone.put("status","failed");
//                // ciTypeId
//                respone.put("message","根据ciTypeId找不到对应的resource_map");
//                return respone;
//            }
//        }
//
//        /**
//         * 将I6000属性转换成CMDB的属性
//         *
//         * @param I6000Data I6000数据
//         * @param resourceMap 映射数据
//         *
//         * @return prepare CMDB的属性map
//         * */
//        private Map<String,String> traToCmdbData(JSONObject I6000Data,Map<String, String> resourceMap
//                ,String classCode){
//            Map<String,String> prepare = new HashMap<>();
//            List<String> toBeNull = new ArrayList<>();
//            for(Map.Entry<String,String> resEntry : resourceMap.entrySet()){
//                // resourceMap，key是I6000属性，value是CMDB属性
//                if(I6000Data.get(resEntry.getKey())==null){
//                    toBeNull.add(resEntry.getKey());
//                    continue;
//                }
//                if(resEntry.getKey().equals("CYCLE_STATUS")||resEntry.getKey().equals("NETWORK")){
//                    continue;
//                }
//                if(!StringUtils.equals("",I6000Data.get(resEntry.getKey()).toString())){
//                    // 转成CMDB属性的数据集
//                    prepare.put(resEntry.getValue(),I6000Data.get(resEntry.getKey()).toString());
//                }
//            }
//            prepare.put("classCode",classCode);
//            // 需要额外定义一个name字段，因为现CMDB中name是关键属性，暂时定义
//            prepare.put("name",prepare.get("cq_bzqc"));
//            return prepare;
//        }
//
//        /**
//         * 调用I6000接口，获得指定模型下，所有资源数据
//         * @param ciTypeId 模型ID
//         * @param fileds I6000查询时，需要的查询属性
//         *
//         * @return itemI6000 以CI_ID为key，返回map类型的资源数据
//         * */
//        private Map<String,JSONObject> getI6000Resource(String ciTypeId, String fileds){
//            JSONObject requestBody = new JSONObject();
//            requestBody.put(Constants.APP_CODE, dataFusionValues.getAppCode());
//            requestBody.put(Constants.USERID, dataFusionValues.getUserId());
//            requestBody.put(Constants.RUN_CORP_CODE, dataFusionValues.getRunCorpCode());
//            requestBody.put(Constants.ATTR_CODE,fileds);
//            requestBody.put(Constants.PAGE_SIZE, "100");
//            int pageNum = 1;
//            int size;
//            // 构造I6000的对比数据，CI_ID为key，具体的数据为value
//            Map<String,JSONObject> itemI6000 = new HashMap<>(32);
//            do{
//                requestBody.put(Constants.PAGE_START, String.valueOf(pageNum));
//                // 请求接口
//                String resultAll =  i6000Api.queryCi(requestBody,ciTypeId);
//                // 将结果转换为对象
//                JSONObject resultObjectAll = parseObject(resultAll);
//                JSONObject resultValueAll = resultObjectAll.getJSONObject("resultValue");
//                JSONArray I6000ITS = resultValueAll.getJSONArray("items");
//                if(I6000ITS==null||CollectionUtils.isEmpty(I6000ITS)){
//                    log.info("轮询I6000接口结束，第"+pageNum+"页结束");
//                    break;
//                }
//                for(Object object : I6000ITS){
//                    JSONObject data  = (JSONObject)object;
//                    itemI6000.put(data.get("CI_ID").toString(),data);
//                }
//                size = I6000ITS.size();
//                pageNum++;
//            }while (size == 100);
//            return itemI6000;
//        }
//
//
//        /**
//         * 调用CMDB接口，获得指定模型下，所有资源数据
//         * @param value CONDITIONS需要的参数，模型ID值
//         *
//         * @return itemCMDB 以cq_pzxid为key，返回map类型的资源数据
//         * */
//        private Map<String,JSONObject> getCmdbResource(String value){
//            Params params = new Params();
//            params.setField("classCode");
//            params.setValue(value);
//            List<Params> paramsList = new ArrayList<>();
//            paramsList.add(params);
//            JSONObject requestBody = new JSONObject();
//            requestBody.put(Constants.PAGE_SIZE, "100");
//            Map<String,JSONObject> itemCMDB = new HashMap<>(32);
//            // 用于构造cmdb接口查询条件
//            requestBody.put(Constants.CONDITIONS,paramsList);
//            int pageNum = 0;
//            int size;
//            do{
//                requestBody.put(Constants.PAGE_NUM, pageNum);
//                String result =  uyunApi.queryCmdbByField(requestBody);
//                JSONObject resultCMDBAll = parseObject(result);
//                String empty = resultCMDBAll.getString("empty");
//                JSONArray cmdbData = resultCMDBAll.getJSONArray("dataList");
//                // cmdb的接口返回值，若为false，代表datalist有数据*/
//                if(StringUtils.equals("false",empty)){
//                    // CMDB下资源信息——对应c_pxzid
//                    for(Object object : cmdbData){
//                        JSONObject data  = (JSONObject)object;
//                        itemCMDB.put(data.get("cq_pzxid").toString(),data);
//                    }
//                }
//                size = cmdbData.size();
//                pageNum++;
//            }while (size == 100);
//            return itemCMDB;
//        }
//
//        /**
//         * 根据CI_ID查询，返回CMDB需要更新的值
//         *
//         * @param comparingV I6000和CMDB的映射关系,key是I6000的属性，value是CMDB的属性
//         * @param I6000 I6000的值
//         * @param cmdb cmdb的值
//         * @return updataData 待更新的属性和值
//         * */
//        private Map<String,String> comparingCmdb(JSONObject I6000,JSONObject cmdb,
//                                                 Map<String,String> comparingV,String classCode,String id){
//            //返回需要更新的属性和值*/
//            Map<String,String> updataData = new HashMap<>(64);
//            List<String> toBeNull = new ArrayList<>();
//            // resEntry——映射字段 */
//            for(Map.Entry<String,String> resEntry : comparingV.entrySet()){
//                // I6000中，没有找到的属性，暂时记录下来*/
//                if(I6000.get(resEntry.getKey())==null){
//                    toBeNull.add(resEntry.getKey());
//                    continue;
//                }
//                if(resEntry.getKey().equals("CYCLE_STATUS")||resEntry.getKey().equals("NETWORK")){
//                    continue;
//                }
//                // 比较I6000和CMDB相同属性的值是否一样，不一样的，就是待更新属性*/
//                if(!StringUtils.equals(I6000.get(resEntry.getKey()).toString().trim(),
//                        cmdb.get(resEntry.getValue())==null?"":cmdb.get(resEntry.getValue()).toString().trim())){
//                    updataData.put(resEntry.getValue(),I6000.get(resEntry.getKey()).toString());
//                }
//            }
//            log.info("I6000中没有找到的属性，{}",toBeNull.toString());
//            updataData.put("classCode",classCode);
//            updataData.put("id",id);
//            return updataData;
//        }
//    }


}
