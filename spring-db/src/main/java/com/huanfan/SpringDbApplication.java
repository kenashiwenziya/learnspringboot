package com.huanfan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.huanfan.dao")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDbApplication.class, args);
    }

}
