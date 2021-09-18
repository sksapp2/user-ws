package com.infc.ms.user.service.impl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.infc.ms.user.dto.SignUpResponse;
import com.infc.ms.user.model.UserModel;
import com.infc.ms.user.respository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;

import com.infc.ms.user.dto.SignUpRequest;
import com.infc.ms.user.service.UserService;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Mono<SignUpResponse> createUser(final SignUpRequest signUpRequest) {
        log.info("sign up user {}", signUpRequest);
        Mono<UserModel> user = userRepository.findByMobileNumber(signUpRequest.getMobileNumber());
        log.info("user from db {}", user);
        return user.flatMap(userDb ->
                        generateJwtMono(userDb)
                ).switchIfEmpty(Mono.defer(() -> asyncInsertUser(signUpRequest))).
                switchIfEmpty(Mono.error(new Throwable("User not inserted")));

    }

    private SignUpResponse generateJwt(UserModel userModel) {
        return SignUpResponse.builder().token("12322").build();
    }

    private Mono<SignUpResponse> generateJwtMono(UserModel userModel) {
        return Mono.just(generateJwt(userModel));

    }
    private Mono<SignUpResponse> asyncInsertUser(SignUpRequest signUpRequest) {
        log.info("insert into db {}", signUpRequest);
        String userId = signUpRequest.getMobileNumber() + "-" + signUpRequest.getDeviceNumber();
        UserModel user = UserModel.builder().
                mobileNumber(signUpRequest.getMobileNumber()).
                countryPhoneCode(signUpRequest.getCountryPhoneCode()).
                deviceNumber(signUpRequest.getDeviceNumber()).
                createdDateTime(LocalDateTime.now(Clock.systemUTC())).
                operator(signUpRequest.getOperator()).
                build();

        Mono<UserModel> userModelMono = userRepository.save(user).
                onErrorReturn(UserModel.builder().build()).
                switchIfEmpty(Mono.just(UserModel.builder().build()));

        return userModelMono.flatMap(userDb ->
                        generateJwtMono(userDb))
                .switchIfEmpty(Mono.defer(() ->
                        Mono.just(SignUpResponse.builder().build())
                ));


    }

    private Mono<SignUpResponse> asyncEmptySignResponse(SignUpRequest u) {
        return Mono.just(SignUpResponse.builder().build());
    }

    private void onError(Throwable throwable, Object o) {
        log.info("exception saving record {}", throwable.getMessage());

    }

}
