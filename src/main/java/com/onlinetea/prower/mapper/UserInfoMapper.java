package com.onlinetea.prower.mapper;

import com.onlinetea.prower.Bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserInfoMapper {

    /**查询用户信息,根据用户ID*/
    UserInfo queryUserInfoById(@Param("userId") String userId);


    /**查询用户信息*/
    UserInfo queryUserInfoByName(@Param("userName") String userName);

    /**用户注册*/
    int logonUser(UserInfo user);

}
