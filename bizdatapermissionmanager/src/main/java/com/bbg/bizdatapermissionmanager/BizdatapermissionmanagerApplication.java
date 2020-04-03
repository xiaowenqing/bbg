package com.bbg.bizdatapermissionmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = { "com.bbg", "com.cloud" })
@EnableEurekaClient
@MapperScan(basePackages = { "com.bbg.bizdatapermissionmanager.dao" })
@SpringBootApplication(scanBasePackages = { "com.bbg" , "com.cloud"})
public class BizdatapermissionmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizdatapermissionmanagerApplication.class, args);
	}

}
