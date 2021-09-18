package com.infc.ms.user.service.impl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.infc.ms.user.dto.SignUpResponse;
import com.infc.ms.user.dto.internal.UserDataRequest;
import com.infc.ms.user.model.UserModel;
import com.infc.ms.user.respository.UserRepository;
import com.infc.ms.user.service.JwtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.infc.ms.user.dto.SignUpRequest;
import com.infc.ms.user.service.UserService;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtService jwtService;

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
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userModel.getUserId());
        userData.put("mobileNumber", userModel.getMobileNumber());
        UserDataRequest.builder().data(userData);
        String token = jwtService.generateJwtToken(UserDataRequest.builder().data(userData).build(), "sasasa");
        return SignUpResponse.builder().token(token).build();
    }

    private Mono<SignUpResponse> generateJwtMono(UserModel userModel) {
        return Mono.just(generateJwt(userModel));

    }

    private Mono<SignUpResponse> asyncInsertUser(SignUpRequest signUpRequest) {
        log.info("insert into db {}", signUpRequest);
        UserModel user = UserModel.builder().
                mobileNumber(signUpRequest.getMobileNumber()).
                countryPhoneCode(signUpRequest.getCountryPhoneCode()).
                createdDateTime(LocalDateTime.now(Clock.systemUTC())).
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

}
