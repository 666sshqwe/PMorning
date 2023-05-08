package com.onlinetea.prower.View;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInfoVo {

    /**主键*/
    long cId;

    /**用户名*/
    String userName;

    /**性别*/
    String userSex;

    /**注册时间*/
    LocalDateTime createTime;

    /**玩过的剧本*/
    List<String> dramaStory;

}
