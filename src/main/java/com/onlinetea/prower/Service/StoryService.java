package com.onlinetea.prower.Service;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.DramaInfo;
import com.onlinetea.prower.Bean.DramaRole;
import com.onlinetea.prower.View.SessionsVo;

import java.util.List;

public interface StoryService {

    long creatSessions(SessionsVo sessionsVo);

    JSONObject querySession(String sessionId);


    List<DramaInfo> getDramaInfo(String type);


    List<DramaRole> getDramaRole(String dramaId);
}
