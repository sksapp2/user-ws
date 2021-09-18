package com.infc.ms.user.service;

import com.infc.ms.user.dto.SignUpResponse;
import com.infc.ms.user.dto.SignUpRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {
	Mono<SignUpResponse> createUser (SignUpRequest request);

}
