package com.seichigo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.seichigo") 
@MapperScan("com.seichigo.mapper")
public class SeichiGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeichiGoApplication.class, args);
	}
}
