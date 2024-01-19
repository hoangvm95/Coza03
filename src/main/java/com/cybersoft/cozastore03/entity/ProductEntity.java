package com.cybersoft.cozastore03.entity;

import lombok.Data;

import javax.persistence.*;
//JPA Buddy, Generater POJO
@Data
@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "images")
    private String images;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "tags")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private CategoryEntity category;


}
