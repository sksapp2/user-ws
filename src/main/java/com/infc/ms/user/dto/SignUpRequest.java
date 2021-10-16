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
	@NotNull(message = "{user.mobile.notnull}")
	private String mobileNumber;
	@NotBlank(message = "{user.country.code.blank}")
	//@Pattern(regexp = "^[0-9]{5}$",message = "{user.country.phone.code.error}")
	private String countryPhoneCode;

	@NotBlank(message = "{user.device.number.not.blank}")
	private String deviceId;

	@NotBlank(message = "{user.device.number.not.blank}")
	private  String publicKey;


}
