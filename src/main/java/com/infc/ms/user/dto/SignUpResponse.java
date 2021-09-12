package com.infc.ms.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class SignUpResponse {
    private String token;
    private List<String> contacts;

}
