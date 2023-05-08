package com.onlinetea.prower.Controller;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Service.StoryService;
import com.onlinetea.prower.Service.StorySessionService;
import com.onlinetea.prower.View.SessionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/paly")
public class PlayDramaController {


    @Autowired
    StoryService storyService;

    @Autowired
    StorySessionService storySessionService;

    /**
     * 展示所有可玩场次
     *
     * */
    @GetMapping("/getSessions")
    public List<SessionsVo> getSessions(@RequestParam String type) {
        return storySessionService.getSessions(type);
    }


    /**
     * 创建场次
     * 点击进入游戏，即注册一个游戏场景
     * */
    @PostMapping("/CSessions")
    public long crStory(@RequestBody SessionsVo sessionsVo) {
        return storyService.creatSessions(sessionsVo);
    }


    /**
     * 随机一个角色
     * 一个用户只能参加一场剧本会，只有结束了，或者该剧本会解散了
     * 才能加入另一个
     * */
    @GetMapping("/randomRole")
    public JSONObject randomRole(@RequestParam String sessionId, @RequestParam String userId) {
        return storySessionService.randomRole(sessionId,userId);
    }
}
