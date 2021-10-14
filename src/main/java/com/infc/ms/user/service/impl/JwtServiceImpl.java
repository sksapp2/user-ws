package com.infc.ms.user.service.impl;

import com.infc.ms.user.dto.internal.UserDataRequest;
import com.infc.ms.user.service.JwtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Key;

@Log4j2
@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateJwtToken(UserDataRequest userDataRequest, String secret) throws RuntimeException {
    return null;
    }




    private String getToken(Key hmacKey, UserDataRequest userDataRequest) throws RuntimeException {
        return null;

    }
    @Override
    public String parseToken(String secret,String token) throws RuntimeException {
       return null;
    }

}
