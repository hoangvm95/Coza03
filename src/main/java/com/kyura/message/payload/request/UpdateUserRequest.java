package com.kyura.message.payload.request;

import com.kyura.message.util.Constant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest extends SignupRequest{
	@NotNull
	private Long id;
}
