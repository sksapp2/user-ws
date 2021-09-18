package com.infc.ms.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SignUpRequest {

	@NotNull(message = "{user.mobile.notnull}")
	private String mobileNumber;

	private String operator;


	//@NotNull(message = "{user.mobile.null}")
	private String deviceNumber;
	

	//@NotNull(message = "{user.country.code.blank}")
	private String countryPhoneCode;

	private List<UserContactDetails> userContactDetails;
	


}
