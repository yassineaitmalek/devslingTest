package com.devsling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsling.config.LoggingListener;

@SpringBootApplication
public class DevslingApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(DevslingApplication.class);
        application.addListeners(new LoggingListener());
        application.run(args);

    }

}
