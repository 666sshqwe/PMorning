package com.onlinetea.prower.Service;

import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.UserInfo;

public interface LoginService {

    JSONObject doLogin(UserInfo user);

    JSONObject doLogon(UserInfo user);
}
