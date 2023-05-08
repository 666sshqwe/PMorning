package com.onlinetea.prower.Config.LoginConfig;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Slf4j
public class JwtUtil {

    // 过期时间
    private static final long EXPIRE_TIMR = 60*24*60;

    // 秘钥
    private static final String SECRET = "lihongru";

    /**
     * 生成Token
     * 根据用户名，加密密钥，过期时间，生成token
     *
     * */
    public static String createToken(String username,String secret,long expireTime){
        return getJwtInfo(username,secret,expireTime);
    }

    public static boolean verify(String token,String username){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username",username)
                    .build();

            verifier.verify(token);
            return true;

        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    /**
     * 获得用户名
     *
     * */
    public static String getUsername(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return  jwt.getClaim("username").asString();
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    /**
     * 签名方法
     * 固定使用EXPIRE_TIMR时间，作为过期时间
     *
     * */
    public static String sign(String username,String secret){
       return getJwtInfo(username,secret,EXPIRE_TIMR);
    }


    /**
     * 根据用户名和密钥生成token
     *
     * */
    public static String getJwtInfo(String username,String secret,long expireTime){
        try {
            Date date = new Date(System.currentTimeMillis()+expireTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withClaim("username",username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.info(e.getMessage());
            return null;
        }
    }

}
