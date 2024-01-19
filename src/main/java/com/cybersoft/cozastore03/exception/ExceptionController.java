package com.cybersoft.cozastore03.exception;

import com.cybersoft.cozastore03.payload.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class ExceptionController {
    Logger logger;

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<?> handlerException(ProductNotFoundException e){
        logger = LoggerFactory.getLogger(ProductNotFoundException.class);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        baseResponse.setMesssage(e.getTitleError());

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
