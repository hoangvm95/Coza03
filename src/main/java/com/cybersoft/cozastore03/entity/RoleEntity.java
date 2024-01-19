package com.cybersoft.cozastore03.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role") //Lưu ý : tên thuộc tính của entity bên UserEntity
    private List<UserEntity> users;

}
