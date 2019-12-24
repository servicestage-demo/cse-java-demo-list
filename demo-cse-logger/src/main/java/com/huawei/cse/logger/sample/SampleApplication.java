package com.huawei.cse.logger.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.servicecomb.springboot.starter.provider.EnableServiceComb;
import org.apache.servicecomb.tracing.zipkin.EnableZipkinTracing;

@SpringBootApplication
@EnableServiceComb
@EnableZipkinTracing
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}
}
