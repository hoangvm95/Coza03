package com.cybersoft.cozastore03.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    private int id;
    private String categoryName;
}
