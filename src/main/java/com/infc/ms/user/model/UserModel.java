package com.infc.ms.user.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Table("user_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserModel {



    @Id
    @Column("user_id")
    private BigInteger userId;

    @Column("mobile_number")
    private String mobileNumber;

    @Column("operator")
    private String operator;

    @Column("device_number")
    private String deviceNumber;

    @Column("country_phone_code")
    private String countryPhoneCode;

    @Column("created_date_time")

    private LocalDateTime createdDateTime;

    boolean isNew() {
        return userId == null;
    }


}
