package com.kyura.message.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse<T> {
	private String message;
	private T data;

	public MessageResponse(String message) {
	    this.message = message;
	}
}
