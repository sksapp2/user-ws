package com.infc.ms.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
@Data
public class UserModel {

    @Id
    private String mobileNo;
    private String operator;
    private String deviceNumber;
    private String countryPhoneCode;

}
