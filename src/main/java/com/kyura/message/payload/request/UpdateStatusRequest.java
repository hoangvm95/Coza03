package com.kyura.message.payload.request;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.util.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateStatusRequest {
	@NotNull(message = Constant.USER_ID_REQUIRED)
	private Long userId;
	@NotNull(message = Constant.USER_STATUS_REQUIRED)
	private ACTIVE_STATUS status;
}
