package com.infc.ms.user.handlers;

import com.infc.ms.user.common.ValidatorHandler;
import com.infc.ms.user.dao.UserDAO;
import com.infc.ms.user.dto.SignUpRequest;
import com.infc.ms.user.dto.SignUpResponse;
import com.infc.ms.user.enums.UserStatusEnum;
import com.infc.ms.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

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
                    flatMap(userModelMono-> ServerResponse.ok().body(getSignUpResponse(userModelMono),SignUpResponse.class));



    }




    private Mono<SignUpResponse> getSignUpResponse(Mono<UserModel> userModel) {
        log.info("token generated ");
       return  Mono.fromSupplier(()-> SignUpResponse.builder().message("Welcome").build());

    }

}
