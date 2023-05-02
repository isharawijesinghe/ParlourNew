package com.ss.parlour.streamservice;

import com.ss.parlour.streamservice.configurations.dataSourceConfig.CassandraDatastaxAstraConfig;
import io.ap4k.kubernetes.annotation.KubernetesApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@KubernetesApplication
@EnableConfigurationProperties({CassandraDatastaxAstraConfig.class})
public class StreamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
