package com.infc.ms.user.service;

import com.infc.ms.user.dto.internal.UserDataRequest;

public interface JwtService {
    String generateJwtToken(UserDataRequest userDataRequest,String secret) throws RuntimeException;
    String parseToken(String secret,String token) throws RuntimeException;

}
