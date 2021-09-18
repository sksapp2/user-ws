package com.infc.ms.user.respository;

import com.infc.ms.user.model.UserModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserModel, BigInteger> {
    Mono<UserModel> findByMobileNumber(String mobileNo);

}
