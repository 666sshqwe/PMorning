package com.onlinetea.prower.Service;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.StorySessionDetail;
import com.onlinetea.prower.View.SessionsVo;

import java.util.List;

public interface StorySessionService {

    List<StorySessionDetail> querySessionDetail(String sessionId,String type);


    JSONObject randomRole(String sessionId,String userId);

    List<SessionsVo> getSessions(String type);

}
