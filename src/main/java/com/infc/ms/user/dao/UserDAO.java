package com.infc.ms.user.dao;

import com.infc.ms.user.model.UserModel;
import reactor.core.publisher.Mono;

public interface UserDAO {

     Mono<UserModel>getUserByMobileNumber(String mobileNumber);
     Mono<UserModel>saveUser(UserModel userModel);

}
