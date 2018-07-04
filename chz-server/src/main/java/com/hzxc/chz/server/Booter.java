package com.hzxc.chz.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
@EnableJpaRepositories(basePackages = "com.hzxc.chz.dao")
@EntityScan(basePackages = "com.hzxc.chz.entity")
public class Booter {
    
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Booter.class) ;
		app.addInitializers(new ApplicationStartup());
		app.run(args);
	}
	
}
