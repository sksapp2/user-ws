package com.infc.ms.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SignUpRequest {

	@NotBlank(message = "{user.mobile.notnull}")
	//@Pattern(regexp = "/\s/g",message = "Space not allowed in mobile number")
	private String mobileNumber;

	@NotBlank(message = "{user.country.phone.code.not.blank}")

	private String countryPhoneCode;

	@NotBlank(message = "{user.device.number.not.blank}")
	private String deviceId;

	@NotBlank(message = "{user.device.number.not.blank}")
	private  String publicKey;


}
