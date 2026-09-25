package com.rabbit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rabbit.mapper") //
public class RabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitApplication.class, args);
	}

}
