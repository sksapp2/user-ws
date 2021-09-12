package com.infc.ms.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Setter
@Getter
@Builder
public class UserModel {

    private String mobileNo;
    private String operator;
    private String deviceNumber;
    private String countryPhoneCode;

}
