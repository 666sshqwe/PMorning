package com.onlinetea.prower;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@MapperScan("com.onlinetea.prower.mapper")
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ProwerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProwerApplication.class, args);
    }

}
