package com.rabbit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Rabbit 电商后端启动类
 * 成员 D 负责项目骨架
 */
@SpringBootApplication
@MapperScan("com.rabbit.mapper")
public class RabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
        System.out.println("========================================");
        System.out.println("  Rabbit 电商后端启动成功！");
        System.out.println("  接口文档: http://localhost:8080/doc.html");
        System.out.println("========================================");
    }
}