package com.onlinetea.prower.Bean;

import lombok.Data;
import java.util.List;

@Data
public class SessionsInfo {

    /**场景ID*/
    long SessionId;

    /**剧本名*/
    String storyName;

    /**角色*/
    List<RoleInfo> storyRoles;

    /**剧本照-背景，故事简介,图片地址*/
    List<String> storyImage;

    /**角色简介图片，图片地址*/
    List<String> roleImages;

}
