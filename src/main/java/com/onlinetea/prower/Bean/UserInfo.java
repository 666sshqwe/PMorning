package com.onlinetea.prower.Bean;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class UserInfo {

    /**用户名*/
    String username;

    /**密码*/
    String password;

    /**对应的角色*/
    List<String> roles;
}
