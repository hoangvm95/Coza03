package com.kyura.message.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewPasswordRequest {
	@NotBlank
	private String code;
	@NotBlank
	private String password;
}
