package com.infc.ms.user.config.routers;

import com.infc.ms.user.handlers.UserHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
@AllArgsConstructor
public class UserRouter {
    private UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> createUserRouters() {
        return RouterFunctions.route()
                .POST("/signUp",
                        RequestPredicates.contentType(MediaType.APPLICATION_JSON),
                        userHandler::createUser).build();


    }
}
