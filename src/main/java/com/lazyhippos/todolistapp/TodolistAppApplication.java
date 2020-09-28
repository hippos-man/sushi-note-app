package com.lazyhippos.todolistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistAppApplication {

//    @Bean
//    public PasswordEncoder encoder() {
//        return new BCryptPasswordEncoder();
//    }


    public static void main(String[] args) {
        SpringApplication.run(TodolistAppApplication.class, args);
    }

}
