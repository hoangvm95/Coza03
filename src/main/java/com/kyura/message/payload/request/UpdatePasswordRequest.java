package com.kyura.message.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdatePasswordRequest {
	@NotBlank
	private String oldPass;
	@NotBlank
	private String password;
}
