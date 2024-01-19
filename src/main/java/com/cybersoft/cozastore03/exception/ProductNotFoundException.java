package com.cybersoft.cozastore03.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductNotFoundException extends RuntimeException{
    private String titleError;
    private String message;
}
