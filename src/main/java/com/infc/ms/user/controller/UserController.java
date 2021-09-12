package com.infc.ms.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
    public List<String> createUser(@Valid @RequestBody SignUpRequest request) throws Throwable {
        List<String> list = new ArrayList<String>();
        userService.createUser(request);
        return list;
    }

}
