package com.infc.ms.user.service.impl;

import com.infc.ms.user.dto.internal.UserDataRequest;
import com.infc.ms.user.service.JwtService;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateJwtToken(UserDataRequest userDataRequest, String secret) throws RuntimeException {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
        return getToken(hmacKey, userDataRequest);
    }


    private String getToken(Key hmacKey, UserDataRequest userDataRequest) throws RuntimeException {
        String jwtToken = null;
        Objects.requireNonNull(hmacKey,"hmac key is null");
        Objects.requireNonNull(userDataRequest,"userDataRequest is null");
        try {
             if(userDataRequest.getData().size()==0){
                throw  new RuntimeException("User data is empty in map");
            }
            Long randomSubject = ThreadLocalRandom.current().nextLong(0, 5);
            Instant now = Instant.now();
            jwtToken = Jwts.builder().addClaims(userDataRequest.getData())
                    .setSubject(String.valueOf(randomSubject))
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                    .signWith(hmacKey)
                    .compact();
        } catch (RuntimeException  th) {
            log.error("Error in generate token : {}", th.getMessage());
            throw th;
        }
        return jwtToken;

    }

}
