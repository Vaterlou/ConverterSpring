package com.example.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public static void req_to_the_bank() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestBean restBean = new RestBean();
        RestTemplate template = restBean.restTemplate(builder);
        restBean.run(template);
    }
}