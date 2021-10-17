package com.infc.ms.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Table("tm_user")
public class UserModel implements Serializable {

    @Column("user_id")
    private String userId;


    @Column("mobile_number")
    private String mobileNumber;

    @Column("public_key")
    private String publicKey;

    @Column("country_phone_code")
    private String countryPhoneCode;
    @Column("device_id")

    private String deviceId;
    @Column("status")

    private String status;

    @Column("created_date_time")

    private LocalDateTime createdDateTime;


}
