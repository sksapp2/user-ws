package com.infc.ms.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.infc.ms.user.dto.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.infc.ms.user.dto.SignUpRequest;
import com.infc.ms.user.service.UserService;

import reactor.core.publisher.Mono;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/signUp")
    public Mono<SignUpResponse> createUser(@Valid @RequestBody SignUpRequest request) throws Throwable {
        Mono<SignUpResponse> mono=userService.createUser(request);
        return mono;
    }

}
