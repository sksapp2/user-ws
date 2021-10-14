package com.infc.ms.user.service;

import com.infc.ms.user.dto.SignUpResponse;
import com.infc.ms.user.dto.SignUpRequest;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Service
public interface UserService {
	Mono<SignUpResponse> createUser (SignUpRequest request)throws RuntimeException;

}
