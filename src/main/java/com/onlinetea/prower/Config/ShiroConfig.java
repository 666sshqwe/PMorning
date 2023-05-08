package com.onlinetea.prower.Config;


import com.onlinetea.prower.Config.LoginConfig.JWTFilter;
import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {

    @Bean("factoryBean")
    public ShiroFilterFactoryBean factoryBean(DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);

//        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("jwt",new JWTFilter());
//        factoryBean.setFilters(filterMap);
//        // 添加默认过滤器
//        factoryBean.setLoginUrl("/login");
//
//        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/login","anon");
//        filterChainDefinitionMap.put("/checkToken","anon");
//
//
//        // 所有url都必须通过jwt认证，才可以访问到
//        filterChainDefinitionMap.put("/**","jwt");
//        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }


    /**
     * 创建DefaultWebSecurityManager
     * 并将自定义的realm注入到DefaultWebSecurityManager
     *
     * */
    @Bean
    public DefaultWebSecurityManager manager(MyShiroRealm myShiroRealm){
        DefaultWebSecurityManager manager  = new DefaultWebSecurityManager();
        manager.setRealm(myShiroRealm);

        // 关闭shiro的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator storageEvaluator = new DefaultSessionStorageEvaluator();
        storageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(storageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }


    /**
     * 设置自定义的realm
     *
     * */
    @Bean
    public MyShiroRealm myShiroRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        MyShiroRealm realm = new MyShiroRealm();
//        realm.setCredentialsMatcher(matcher);
        return realm;
    }


    /**
     * 设置密码的匹配器
     *
     * */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密方式
        matcher.setHashAlgorithmName("MD5");
        // 设置哈希迭代次数
        matcher.setHashIterations(1024);
        // 设置存储凭证（true:十六进制,false:base64）
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }


    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("factoryBean");
        filterBean.setFilter(proxy);
        return filterBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttri(SecurityManager manager){
        AuthorizationAttributeSourceAdvisor authorization = new AuthorizationAttributeSourceAdvisor();
        authorization.setSecurityManager(manager);
        return authorization;
    }

}
