package com.onlinetea.prower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ProwerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProwerApplication.class, args);
    }

}
