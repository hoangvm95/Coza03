package com.kyura.message.payload.response;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.common.ROLE;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserResponse {
	private Long id;
	private String username;
	private String email;
	private String phone;
	private String phoneCountryCode;
	private String phoneNationalNumber;
	private ACTIVE_STATUS status;
	private Long createdAt;
	Set<ROLE> roles = new HashSet<>();
}
