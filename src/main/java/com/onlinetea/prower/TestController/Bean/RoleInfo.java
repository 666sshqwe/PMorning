package com.onlinetea.prower.TestController.Bean;

import lombok.Data;

import java.io.Serializable;
import io.protostuff.Tag;


@Data
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**预留主键*/
    @Tag(1)
    long id;

    /**角色ID*/
    @Tag(2)
    long roleId;

    /**角色名*/
    @Tag(3)
    String roleName;

    /**用户ID*/
    @Tag(4)
    long userID;

    /**年龄*/
    @Tag(5)
    int age;

    /**任务信息*/
    @Tag(6)
    String taskInfo;

    /**剧情*/
    @Tag(7)
    String info;

}
