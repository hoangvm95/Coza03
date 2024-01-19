package com.cybersoft.cozastore03.payload.response;

import lombok.Data;
//work flow
@Data
public class BaseResponse {
    private int statusCode = 200;
    private String messsage = "";
    private Object data;
}
