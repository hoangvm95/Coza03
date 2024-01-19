package com.kyura.message.models;


import com.kyura.message.common.TypeImage;
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
@Table(	name = "composite_id")

public class CompositeId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long idTarget;

    @Enumerated(EnumType.STRING)
    @Column(name = "idType")
    private TypeImage idType;

    private String image;

}
