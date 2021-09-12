package com.infc.ms.user.respository;

import com.infc.ms.user.model.UserModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserModel, String> {
    Mono<UserModel> findByFirstMobileNumber(String mobileNo);

}
