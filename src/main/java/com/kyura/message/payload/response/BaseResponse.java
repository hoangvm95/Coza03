package com.kyura.message.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private int statusCode = 200;
    private String message ="";
    private Object data = "";
}
