package com.infc.ms.user.service.impl;

import java.util.Arrays;
import java.util.List;

import com.infc.ms.user.dto.SignUpResponse;
import com.infc.ms.user.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infc.ms.user.dto.SignUpRequest;
import com.infc.ms.user.service.UserService;

import reactor.core.publisher.Flux;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Flux<List<SignUpResponse>> createUser(SignUpRequest request) {
		return Flux.from(Flux.just(Arrays.asList(SignUpResponse.builder().build())));
	}

}
