package com.heb.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
public class S3ClientConfig {

	/*
	 * I'll add the Amazon S3 object to the IOC container as a bean.
	 */
	@Bean
	public S3Client s3Client() {
		
		S3ClientBuilder builder = S3Client.builder();
		builder.region(Region.US_EAST_2);
		S3Client client = builder.build();
		return client;
	}
}
