package com.infc.ms.user.handlers;

import com.infc.ms.user.common.ValidatorHandler;
import com.infc.ms.user.dao.UserDAO;
import com.infc.ms.user.dto.SignUpRequest;
import com.infc.ms.user.dto.SignUpResponse;
import com.infc.ms.user.dto.internal.UserDataRequest;
import com.infc.ms.user.enums.UserStatusEnum;
import com.infc.ms.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserHandler {
    private final UserDAO userDAO;

    final ValidatorHandler validatorHandler;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<SignUpRequest> signUpRequestMono = request.bodyToMono(SignUpRequest.class);
            return signUpRequestMono.doOnNext(validatorHandler::validate).
                    map(signUpRequest ->
                    userDAO.getUserByMobileNumber(signUpRequest.getMobileNumber()
                    ,signUpRequest.getDeviceId(),UserStatusEnum.ACTIVE.getUserStatus())
                            .switchIfEmpty(Mono.defer(()->{ ;
                                return userDAO.saveUser(
                                        UserModel.builder().
                                                 userId(String.valueOf(LocalDateTime.now()))
                                                .mobileNumber(signUpRequest.getMobileNumber())
                                                .countryPhoneCode(signUpRequest.getCountryPhoneCode())
                                                .createdDateTime(LocalDateTime.now())
                                                .deviceId(signUpRequest.getDeviceId())
                                                .publicKey(signUpRequest.getPublicKey())
                                                .status(UserStatusEnum.ACTIVE.getUserStatus())
                                                .build()
                                );
                            }))
                    ).
                    flatMap(userModelMono->{ return
                     ServerResponse.ok().body(generateJwtMono(userModelMono),SignUpResponse.class);
                    });



    }


    private Mono<ServerResponse> createUserAndGenerateToken(SignUpRequest signUpRequest) {
        UserModel user = UserModel.builder().userId(String.valueOf(LocalDateTime.now())).
                mobileNumber(signUpRequest.getMobileNumber()).
                countryPhoneCode(signUpRequest.getCountryPhoneCode()).
                createdDateTime(LocalDateTime.now(Clock.systemUTC())).
                publicKey(signUpRequest.getPublicKey()).deviceId(signUpRequest.getDeviceId()).
                status(UserStatusEnum.ACTIVE.getUserStatus()).
                build();
        log.info("create new user {}", user);
        return ServerResponse.ok().body(userDAO.saveUser(user).flatMap(us -> generateJwtMono(Mono.just(us))),SignUpResponse.class);


    }

    private SignUpResponse generateJwt(UserModel userModel) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userModel.getUserId());
        userData.put("mobileNumber", userModel.getMobileNumber());
        UserDataRequest.builder().data(userData);
        String token = String.valueOf(Math.round(1));
        return SignUpResponse.builder().token(token).build();
    }

    private Mono<SignUpResponse> generateJwtMono(Mono<UserModel> userModel) {
        log.info("token generated ");
        return  userModel.map(m->generateJwt(m));

    }

}
