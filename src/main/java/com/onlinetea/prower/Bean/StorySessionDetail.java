package com.onlinetea.prower.Bean;

import lombok.Data;


@Data
public class StorySessionDetail {

    /**主键*/
    long id;

    /**用户ID*/
    long userId;

    /**剧本角色主键*/
    long roleId;

    /**角色名*/
    String roleName;

    /**剧本名*/
    String dramaName;

    /**场次ID*/
    long storySessionId;

    /**场次类型*/
    String sessionType;

}
