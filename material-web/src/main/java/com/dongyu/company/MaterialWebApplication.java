package com.dongyu.company;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableScheduling
public class MaterialWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaterialWebApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext context){
       return  args -> Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
