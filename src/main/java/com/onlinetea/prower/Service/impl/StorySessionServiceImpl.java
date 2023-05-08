package com.onlinetea.prower.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.DramaRole;
import com.onlinetea.prower.Bean.StorySessionDetail;
import com.onlinetea.prower.Bean.UserInfo;
import com.onlinetea.prower.Service.StorySessionService;
import com.onlinetea.prower.View.SessionsVo;
import com.onlinetea.prower.mapper.StoryMapper;
import com.onlinetea.prower.mapper.StorySessionMapper;
import com.onlinetea.prower.mapper.UserInfoMapper;
import com.onlinetea.prower.mapper.dto.ResourceMappingPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StorySessionServiceImpl implements StorySessionService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    StoryMapper storyMapper;

    @Autowired
    StorySessionMapper storySessionMapper;

    public static String  lock = "lock";

    /**
     * 查询场次信息
     *
     * */
    @Override
    public List<StorySessionDetail> querySessionDetail(String sessionId,String type) {
        return storySessionMapper.querySessionDetail(sessionId,type);
    }

    /**
     * 查看story_session_detail，该表冗余了用户id和角色id的绑定，也表明了属于哪个场次
     * 记录了该场次，已经有几个角色分配了，已经有几个人加入了；可以和剧本的角色表进行对照，查看
     * 还有什么角色可以选择；需要添加字段，已经结束了的场次信息，就需要删除的
     *
     * 应该是等人都集齐后，由创建者统一开始分配角色
     *
     **/
    @Override
    public JSONObject randomRole(String sessionId,String userId) {

      JSONObject result = new JSONObject();
      // 该场次的其他信息-剧本名、参与用户的id，剩余角色
      SessionsVo sessions = storySessionMapper.querySession(sessionId);

      // 用户信息
      UserInfo userInfo = userInfoMapper.queryUserInfoById(userId);

      // 该场次的剧本的人物信息
      List<DramaRole> roles = storyMapper.getDramaRole(String.valueOf(sessions.getDramaId()));

      // 需要加锁，避免并发下，多个用户，同时随机到了一个角色，并新增到表中了
      synchronized (lock){
          // 该场次的任务安排信息
          List<StorySessionDetail> roleInfo = storySessionMapper.querySessionDetail(sessionId,"ready");
          // 只有该场次角色还没有满，才能继续选角色
          if(roleInfo.size() != roles.size()){
              result = addSessionDetail(sessions,roleInfo,roles,userInfo);
          }else{
              result.put("fail","人数已满，无法加入");
          }
      }
      return result;
    }


    /**
     * 页面展示哪些剧本场次，
     * 需要展示场次的剧本名，还剩角色，创建者，场次号
     *
     *
     * */
    @Override
    public List<SessionsVo> getSessions(String type) {
        List<SessionsVo> sessions =  storySessionMapper.queryAllSessions(type);
        for(SessionsVo vo : sessions ){
            vo.setDramaName("第"+vo.getCId()+"场-"+vo.getDramaName());
            vo.setSessionDesc("剩余"+(vo.getRoleNum()-vo.getUserNum())+"个角色");
        }
        return sessions;
    }

    /**
     * 根据已有的场次信息，生成一个随机的
     * 角色-用户对应的信息
     *
     * @param roleInfo 场次信息
     * @param rolesOfSex 同一性别的剧本角色
     * @return result 返回带有roleID的场次信息
     *
     * */
    public StorySessionDetail getSessionDetails(List<StorySessionDetail> roleInfo,List<DramaRole> rolesOfSex){
        List<Long> roled = roleInfo.stream().map(StorySessionDetail::getRoleId).collect(Collectors.toList());
        StorySessionDetail result = new StorySessionDetail();
        for(long value: roled){
            rolesOfSex.removeIf(x->x.getId() == value);
        }
        long roleId = randomRole(rolesOfSex);
        result.setRoleId(roleId);
        return result;
    }

    /**
     * 生成对应的场次的信息，并入库
     *
     * */
    private JSONObject addSessionDetail(SessionsVo sessions,List<StorySessionDetail> roleInfo,
      List<DramaRole> roles,UserInfo userInfo){
        JSONObject reData = new JSONObject();
        StorySessionDetail result = new StorySessionDetail();
        long roleId;
        List<DramaRole> rolesOfMan = roles.stream().filter(x->x.getRoleSex().equals("男")).collect(Collectors.toList());
        List<DramaRole> rolesOfWomen = roles.stream().filter(x->x.getRoleSex().equals("女")).collect(Collectors.toList());
        // 构建角色ID和角色名对应的map
        Map<Long,String> roleNameMap = roles.stream().
                collect(Collectors.toMap(DramaRole::getId,DramaRole::getRoleName));

        // 构建角色ID和剧本名对应的map
        Map<Long,String> dramaNameMap = roles.stream().
                collect(Collectors.toMap(DramaRole::getId,DramaRole::getTitle));
        if(roleInfo!=null&&!roleInfo.isEmpty()){
            // 查询有值，从剩下的角色里随机
            if(StringUtils.equals("男",userInfo.getUserSex())){
                result = getSessionDetails(roleInfo,rolesOfMan);
            }else{
                result = getSessionDetails(roleInfo,rolesOfWomen);
            }
            result.setRoleName(roleNameMap.get(result.getRoleId()));
            result.setDramaName(dramaNameMap.get(result.getRoleId()));
            result.setSessionType("ready");
            result.setUserId(userInfo.getCId());
            result.setStorySessionId(sessions.getCId());
            storySessionMapper.insertSessionDeatil(result);
            reData.put("success","新增角色："+result.getRoleId()+"剧本："+rolesOfMan.get(0).getTitle());
            // 更新story_sessions表，更新新增的人员信息到该表中记录
            SessionsVo data  =new SessionsVo();
            data.setCId(sessions.getCId());
            data.setRoleNum(sessions.getRoleNum()-1);
            data.setUserNum(sessions.getUserNum()+1);
            data.setUserIds(sessions.getUserIds()+"|"+userInfo.getCId());
            data.setUpdateTime(LocalDateTime.now());
            storySessionMapper.updateSession(data);
        }else{
            // 给创建者初始化角色
            // 查询为空，说明暂时还没有角色分配，从所有的角色里随机
            if(StringUtils.equals("男",userInfo.getUserSex())){
                roleId = randomRole(rolesOfMan);
            }else{
                roleId = randomRole(rolesOfWomen);
            }
            result.setRoleId(roleId);
            result.setRoleName(roleNameMap.get(result.getRoleId()));
            result.setDramaName(dramaNameMap.get(result.getRoleId()));
            result.setSessionType("ready");
            result.setUserId(userInfo.getCId());
            result.setStorySessionId(sessions.getCId());
            storySessionMapper.insertSessionDeatil(result);
            reData.put("success","初始化角色："+result.getRoleId()+" | 剧本："+rolesOfMan.get(0).getTitle());
        }

        return reData;
    }


    /**
     * 随机获得角色信息
     *
     * */
    public long randomRole(List<DramaRole> roles){
        // (Math.random()*(max-min+1))+min
        int random = (int)(Math.random()*(roles.size()));
        return roles.get(random).getId();
    }
}

