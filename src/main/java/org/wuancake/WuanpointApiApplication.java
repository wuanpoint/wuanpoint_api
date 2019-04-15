package org.wuancake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * 午安点评SpringBoot生成的启动类
 * @author SpringBoot
 */
@SpringBootApplication
@PropertySource("file:/etc/wuanpoint_application.yml")
public class WuanpointApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WuanpointApiApplication.class, args);
	}

}

