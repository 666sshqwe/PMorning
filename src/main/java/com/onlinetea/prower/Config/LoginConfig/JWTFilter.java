package com.onlinetea.prower.Config.LoginConfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
//@Component
public class JWTFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

//        if(((HttpServletRequest)request).getRequestURI().endsWith("login")){
//            return true;
//        }
//
//        if(isLoginAttempt(request,response)){
//            log.info("该用户已登录");
//
//            try {
//                return executeLogin(request,response);
//            } catch (Exception e) {
//                log.info("JWTFilter fail"+e.getMessage());
//            }
//        }else{
//            log.info("用户没有登录");
//
//
//        }

        return super.isAccessAllowed(request, response, mappedValue);
    }




    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        return null!=httpServletRequest.getHeader("token");
    }


    /**
     * 执行登录
     *
     * */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String token = httpServletRequest.getHeader("token");
        try{
            getSubject(request,response).login(new JwtToken(token));
            return true;
        }catch (Exception e){
            log.info("executeLogin fail"+e.getMessage());
            return false;
        }
    }


    /**
     * 支持跨域
     *
     * */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        httpServletResponse.setHeader("Access-control-Allow-Origin",httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-control-Allow-Headers",httpServletRequest.getHeader("Access-Control-Request-Headers"));

        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
