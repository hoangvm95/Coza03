package com.kyura.message.models;

import com.kyura.message.common.MenuType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="menu_management")
public class MenuManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "menu_type")
    private MenuType menuType;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName ="id")
    private User user;

    @OneToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private Role role;


}
