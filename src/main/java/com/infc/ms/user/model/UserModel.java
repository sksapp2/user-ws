package com.infc.ms.user.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="tm_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserModel extends PanacheEntity implements Serializable {

    @Id
    @Column(name ="user_id")
    private String userId;
    @Column(name ="mobile_number")
    private String mobileNumber;

    @Column(name="public_key")
    private String publicKey;

    @Column(name="country_phone_code")
    private String countryPhoneCode;
    @Column(name ="created_date_time")
    private LocalDateTime createdDateTime;





}
