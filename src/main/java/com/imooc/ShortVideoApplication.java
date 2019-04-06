package com.imooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.imooc.mapper")
@ComponentScan("com.imooc")
@EnableTransactionManagement
public class ShortVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortVideoApplication.class, args);
    }

}
