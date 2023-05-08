package com.onlinetea.prower.Bean;


import lombok.Data;


@Data
public class RoleInfo {

    /**预留主键*/
    long id;

    /**角色ID*/
    long roleId;

    /**角色名*/
    String roleName;

    /**用户ID*/
    long userID;

    /**年龄*/
    int age;

    /**任务信息*/
    String taskInfo;

    /**剧情*/
    String info;

}
