package com.kyura.message.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class CheckPhoneNumberRequest {
	@NotEmpty(message = "INVALID_PHONE_NUMBER")
	private List<String> phoneNumbers;

}
