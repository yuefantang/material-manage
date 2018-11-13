package com.dongyu.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;

import java.io.File;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class MaterialWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaterialWebApplication.class,args);
    }
}
