package com.hzxc.chz.server;

import com.alibaba.fastjson.JSONObject;
import com.hzxc.chz.dao.GxqUserRepository;
import com.hzxc.chz.entity.GxqUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
@EnableJpaRepositories(basePackages = "com.hzxc.chz.dao")
@EntityScan(basePackages = "com.hzxc.chz.entity")
public class Booter {

	private static final Logger logger = LoggerFactory.getLogger(Booter.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Booter.class) ;
		app.addInitializers(new ApplicationStartup());
		app.run(args);
	}

	@Bean
	@Order(3)
	CommandLineRunner demo(GxqUserRepository repository) {
		return (args) -> {
			logger.info("in demo");
			for(GxqUser gxqUser:repository.findAll()) {
				logger.info(JSONObject.toJSONString(gxqUser));
			}
		};
	}

	@Bean
	@Order(8)
	Object aa() {
		logger.info("in aa");
		return null;
	}
}
