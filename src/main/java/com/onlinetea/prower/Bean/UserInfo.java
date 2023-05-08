package com.onlinetea.prower.Bean;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserInfo {
    /**主键*/
    long cId;

    /**账号*/
    String userName;

    /**密码*/
    String userPassword;

    /**手机号*/
    String userPhone;

    /**类型*/
    String cType;

    /**性别*/
    String userSex;

    /**创建时间*/
    LocalDateTime createTime;

    /**加密时，使用的盐*/
    String saltUuid;

}
