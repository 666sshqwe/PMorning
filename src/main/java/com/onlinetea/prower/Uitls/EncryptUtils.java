package com.onlinetea.prower.Uitls;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EncryptUtils {


    /**暂时加密方法
     *
     * */
    public static String encrypt(String secret,String salt){
        SimpleHash encrypt = new SimpleHash("md5",secret,salt,1024);
        return encrypt.toString();
    }

}
