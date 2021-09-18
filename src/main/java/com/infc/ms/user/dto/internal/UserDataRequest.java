package com.infc.ms.user.dto.internal;

import lombok.*;

import java.util.Map;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataRequest {

    private Map<String,Object> data;
}
