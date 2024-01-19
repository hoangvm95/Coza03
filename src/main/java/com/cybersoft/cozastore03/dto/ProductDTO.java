package com.cybersoft.cozastore03.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {
    private int id;
    private String productName;
    private String image;
    private double price;

}
