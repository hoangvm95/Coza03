package com.kyura.message.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kyura.message.common.ACTIVE_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(	name = "specialists")
public class Specialists extends AbstractAuditing{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name="name")
    private String name;

    @NotBlank
    private String thumbnail;

    @NotBlank
    @Column
    private String description;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(20) default 'ACTIVE'")
    private ACTIVE_STATUS status;

    @OneToMany(mappedBy = "specialists")
    @JsonManagedReference
    private Set<PackageClinic> listPackageClinics;

}
