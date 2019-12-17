package org.apache.servicecomb.samples.cas;

import org.apache.servicecomb.springboot.starter.provider.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;

import net.unicon.cas.client.configuration.EnableCasClient;

@SpringBootApplication(exclude = DispatcherServletAutoConfiguration.class)
@EnableCasClient // 启用cas client
@EnableServiceComb
public class CasApplication {
	public static void main(String[] args) {
		SpringApplication.run(CasApplication.class, args);
	}
}
