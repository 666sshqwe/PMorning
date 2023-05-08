package com.onlinetea.prower.TestController;

import com.onlinetea.prower.TestController.Bean.RoleInfo;
import com.onlinetea.prower.TestController.Utils.ProtoBufUtil;

public class ProtoBufMain {

    public static void main(String[] args){
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setAge(10);
        roleInfo.setId(1);
        roleInfo.setInfo("hahaha");

        byte[] roleS = ProtoBufUtil.serializer(roleInfo);
        System.out.println("序列化之后");
        System.out.println(roleS);
        RoleInfo anaRoles = ProtoBufUtil.deserializer(roleS,RoleInfo.class);
        System.out.println("转成bean");
        System.out.println(anaRoles.toString());
    }


}
