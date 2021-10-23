package com.infc.ms.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;



@SpringBootApplication(scanBasePackages = "com.infc.ms.*", exclude = ErrorWebFluxAutoConfiguration.class)
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }



}
