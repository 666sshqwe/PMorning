package com.onlinetea.prower.Service;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.AccessToken;
import com.onlinetea.prower.Uitls.WebUtisServ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
@Slf4j
public class WxService {
      private static   final String TOKEN="bxs666";

    /**验证签名*/
    public static  boolean  check(String timestamp,String nonce,String signature){
        log.info("========== 进入wxservice的check方法，进行校验");
       String[] strs = new String[]{TOKEN,timestamp,nonce};
        log.info("========== 进行check时，获取的strs0:"+strs[0].toString());
        log.info("========== 进行check时，获取的strs1:"+strs[1].toString());
        log.info("========== 进行check时，获取的strs2:"+strs[2].toString());
       Arrays.sort(strs);
       String str = strs[0]+strs[1]+strs[2];
       String mysig = sha1(str);
       return mysig.equals(signature);
    }

    private static String sha1(String src) {

        /**获取一个加密对象*/
        try {
            MessageDigest md = MessageDigest.getInstance("sha1");
            /**加密操作*/
            byte[] digest =  md.digest(src.getBytes());
            char[] chars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
            StringBuilder sb = new StringBuilder();
            for(byte b:digest){
                sb.append(chars[(b>>4)&15]);
                sb.append(chars[b&15]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static final String GET_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String APPID="wx200acd4693f311ae";
    public static final String APPSECRET="24956d5597afdb1bdf63d1e9f52147d7";
    public static AccessToken at;
    @Autowired
    static
    WebUtisServ webUtisServ;
    /**获取token*/
    private static void getToken(){
        log.info("========== 开始获取token");
        String url = GET_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        String tokenStr = webUtisServ.getReDoGet(url);
        JSONObject jsonObject1 =JSONObject.parseObject(tokenStr);
        String token = jsonObject1.getString("access_token");
        String expires = jsonObject1.getString("expires_in");
        at = new AccessToken(token,expires);
        log.info("========== token值为："+at);
    }

    public static String getAccessToken(){
        if(at==null||at.isExpired()){
            getToken();
        }
        return at.getAccessToken();
    }



}
