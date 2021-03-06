package com.infc.ms.user.service.impl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.infc.ms.user.dao.UserDAO;
import com.infc.ms.user.dto.SignUpResponse;
import com.infc.ms.user.dto.internal.UserDataRequest;
import com.infc.ms.user.enums.UserStatusEnum;
import com.infc.ms.user.model.UserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.infc.ms.user.dto.SignUpRequest;
import com.infc.ms.user.service.UserService;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO userDAO;


    @Value("${secret}")
    private String secret;

    @Override
    public Mono<SignUpResponse> createUser(final SignUpRequest signUpRequest) {
        log.info("sign up user {}", signUpRequest);
        Mono<UserModel> user = userDAO.getUserByMobileNumber(signUpRequest.getMobileNumber(),
                signUpRequest.getDeviceId(),UserStatusEnum.ACTIVE.getUserStatus());
        log.info("user from db {}", user.log());
        return user.flatMap(userDb ->
                        generateJwtMono(userDb)
                ).switchIfEmpty(Mono.defer(() -> {
                    try {
                        return asyncInsertUser(signUpRequest);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    return Mono.error(new Throwable("Fail to create User"));
                })).
                switchIfEmpty(Mono.error(new Throwable("User not inserted")));

    }


    private Mono<SignUpResponse> generateJwtMono(UserModel userModel) {
        log.info("token generated ");
        return Mono.just(SignUpResponse.builder().message("Welcome").build());

    }

    private Mono<SignUpResponse> asyncInsertUser(SignUpRequest signUpRequest) throws Throwable {
        log.info("insert into db {}", signUpRequest);
        UserModel user = UserModel.builder().userId(String.valueOf(LocalDateTime.now())).
                mobileNumber(signUpRequest.getMobileNumber()).
                countryPhoneCode(signUpRequest.getCountryPhoneCode()).
                createdDateTime(LocalDateTime.now(Clock.systemUTC())).
                publicKey(signUpRequest.getPublicKey()).deviceId(signUpRequest.getDeviceId()).
                status(UserStatusEnum.ACTIVE.getUserStatus()).
                build();
        log.info("create new user {}", user);
        Mono<UserModel> userModelMono = userDAO.saveUser(user).
                onErrorReturn(UserModel.builder().build()).
                switchIfEmpty(Mono.just(UserModel.builder().build()));
        return userModelMono.flatMap(userDb ->
                        generateJwtMono(userDb))
                .switchIfEmpty(Mono.defer(() ->
                        Mono.just(SignUpResponse.builder().build())
                ));
    }

}
