package com.kyura.message.models;

import com.kyura.message.common.ACTIVE_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hospitals")
public class Hospitals extends AbstractAuditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String desciption;

    @NotBlank
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(20) default 'ACTIVE'")
    private ACTIVE_STATUS status;


    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName ="id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "hospital_speciality",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Specialists> specialists = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "hospital_package",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "packageClinic_id"))
    private Set<PackageClinic> listPackageClinics = new HashSet<>();

    @OneToOne(mappedBy = "hospitals")
    private Schedules schedules;


    @OneToMany(mappedBy = "hospitals")
    private Set<Doctors> listDoctor;

    @OneToOne(mappedBy = "hospitals")
    private Transaction transaction;

}
