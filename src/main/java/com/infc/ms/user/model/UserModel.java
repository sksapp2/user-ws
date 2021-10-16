package com.infc.ms.user.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class UserModel implements Serializable {


    private String userId;


    private String mobileNumber;

    private String publicKey;

    private String countryPhoneCode;

    private String deviceId;


    private String status;


    private LocalDateTime createdDateTime;


}
