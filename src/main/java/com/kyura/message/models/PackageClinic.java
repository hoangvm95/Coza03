package com.kyura.message.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kyura.message.common.ACTIVE_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="package_clinic")
@Entity
public class PackageClinic extends AbstractAuditing{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 40)
    private String name;
    @NotBlank
    private String description;
    private String thumbnail;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String age;
    @NotBlank
    private String gender;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(20) default 'ACTIVE'")
    private ACTIVE_STATUS status;
    @ManyToOne
    @JoinColumn(name="speciality_id")
    @JsonBackReference
    private Specialists specialists;

    @OneToOne(mappedBy = "packageClinic")
    @JsonIgnore
    private Booking booking;

}
