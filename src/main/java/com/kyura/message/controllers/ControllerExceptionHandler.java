package com.kyura.message.controllers;

import com.kyura.message.exception.BadRequestException;
import com.kyura.message.exception.ServerProcessingException;
import com.kyura.message.exception.TokenRefreshException;
import com.kyura.message.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);


	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<MessageResponse> badRequestException(BadRequestException ex, WebRequest webRequest) {
		logger.error("", ex);
		return new ResponseEntity<>(new MessageResponse(ex.getErrorCode()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ServerProcessingException.class)
	public ResponseEntity<MessageResponse> badRequestException(ServerProcessingException ex, WebRequest webRequest) {
		logger.error("", ex);
		return new ResponseEntity<>(new MessageResponse(ex.getErrorCode()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = TokenRefreshException.class)
	public ResponseEntity<MessageResponse> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
		logger.error("", ex);
		return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageResponse> exception(Exception ex, WebRequest webRequest) {
		logger.error("", ex);
		return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
