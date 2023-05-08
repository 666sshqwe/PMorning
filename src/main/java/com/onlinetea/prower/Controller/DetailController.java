package com.onlinetea.prower.Controller;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.DramaInfo;
import com.onlinetea.prower.Bean.DramaRole;
import com.onlinetea.prower.Bean.RoleInfo;
import com.onlinetea.prower.Bean.SessionsInfo;
import com.onlinetea.prower.Service.StoryService;
import com.onlinetea.prower.Service.StorySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/store")
public class DetailController {

    @Autowired
    StoryService storyService;

    @Autowired
    StorySessionService storySessionService;

    @GetMapping("/getInfos")
    public SessionsInfo getInfos() {
        return new SessionsInfo();
    }

    /**
     * 根据剧本，获得剧本的角色信息
     *
     * */
    @GetMapping("/getDramaRole")
    public List<DramaRole> getDramaRole(@RequestParam String dramaId) {
        return  storyService.getDramaRole(dramaId);
    }

    /**
     * 根据类型查询剧本
     *
     * */
    @GetMapping("/getDrama")
    public List<DramaInfo> getDramaInfo(@RequestParam String type) {
        return  storyService.getDramaInfo(type);
    }

    /**
     * 查询场次信息和对应的角色信息
     *
     * */
    @GetMapping("/getSessions")
    public JSONObject getSessions(@RequestParam String sessionId) {
        return storyService.querySession(sessionId);
    }




    /**
     * 如果出现多个传参，接受参数直接使用
     * 实体类，不需要注解标注，就可以接受到
     * 前端只需要传入和实体类对应的参数名和值
     * 即可
     *
     * */
    //http://192.168.43.66:8015/stores/store/roleInfo?storyName=白昼&storyImage=xxxxx
    @GetMapping("/roleInfos")
    public String getRoleInfos(SessionsInfo story) {
        return "mamnyStory";
    }


    @PostMapping("/updateInfo")
    public String updateInfo(@RequestBody RoleInfo role) {

        return "test";
    }

}
