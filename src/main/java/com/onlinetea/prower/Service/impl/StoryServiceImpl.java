package com.onlinetea.prower.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.DramaInfo;
import com.onlinetea.prower.Bean.DramaRole;
import com.onlinetea.prower.Service.StoryService;
import com.onlinetea.prower.View.SessionsVo;
import com.onlinetea.prower.mapper.StoryMapper;
import com.onlinetea.prower.mapper.StorySessionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class StoryServiceImpl implements StoryService {

    @Autowired
    StoryMapper storyMapper;

    @Autowired
    StorySessionMapper storySessionMapper;

    /**
     * 点击创建后，创建一个剧本场次
     *
     *
     * */
    @Override
    public long creatSessions(SessionsVo sessionsVo) {
        log.info("create Session info {}",sessionsVo);
        List<DramaRole> roles = storyMapper.getDramaRole(String.valueOf(sessionsVo.getDramaId()));
        sessionsVo.setUserNum(1);
        sessionsVo.setRoleNum(roles.size());
        sessionsVo.setCreateTime(LocalDateTime.now());
        storySessionMapper.createSessions(sessionsVo);
        return sessionsVo.getCId();
    }

    /**
     * 根据sessionID获得session信息
     *
     * */
    @Override
    public JSONObject querySession(String sessionId) {
        JSONObject result = new JSONObject();
        SessionsVo sessionsVo = storySessionMapper.querySession(sessionId);
        List<DramaRole> roles = storyMapper.getDramaRole(String.valueOf(sessionsVo.getDramaId()));
        result.put("storySession",sessionsVo);
        result.put("roleInfo",roles);
        return result;
    }

    /**
     * 根据类型获得相应的剧本数据
     * type = 硬核
     *
     * */
    @Override
    public List<DramaInfo> getDramaInfo(String type) {
        return storyMapper.getDramaInfos(type);
    }

    /**
     * 根据剧本，获得剧本的角色信息
     * dramaName = ${剧本名}
     *
     * */
    @Override
    public List<DramaRole> getDramaRole(String dramaId) {
        return storyMapper.getDramaRole(dramaId);
    }
}
