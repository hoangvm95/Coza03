package com.cybersoft.cozastore03.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //Thằng nào là chữ N, khóa ngoại thì luôn luôn là @ManyToOne, @JoinColumn(tên cột trong database)
    //Nếu chữ cuối là One thì là một đối tượng của Entity tham chiếu tới
    //Nếu chữ cuối là Many thì là một list đối tượng của Entity tham chiếu tới

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

}
