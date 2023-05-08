package com.onlinetea.prower.TestController.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class OtherBeanConfig {


    @Bean
    public RestTemplate initRestTemplate(){
        return new RestTemplate();
    }

}
