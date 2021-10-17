package com.infc.ms.user.controller;

import javax.validation.Valid;

import com.infc.ms.user.dto.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.infc.ms.user.dto.SignUpRequest;
import com.infc.ms.user.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping(value = "/signUp",consumes = {"application/json"})
    public Mono<SignUpResponse> createUser(@Valid @RequestBody SignUpRequest request) throws Throwable {
        System.out.println("---------post-------------");

        Mono<SignUpResponse> mono=userService.createUser(request);
        return mono;
    }

}
