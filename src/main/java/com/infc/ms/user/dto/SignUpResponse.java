package com.infc.ms.user.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SignUpResponse {
    private String token;

    public static Object buildErrorResponse(Throwable throwable) {
        return  null;
    }
}
