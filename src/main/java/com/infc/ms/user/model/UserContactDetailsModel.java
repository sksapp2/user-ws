package com.infc.ms.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder

public class UserContactDetailsModel {
    private String mobileNo;
    private String contact;

}
