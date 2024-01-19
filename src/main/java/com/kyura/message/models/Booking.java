package com.kyura.message.models;

import com.kyura.message.common.ACTIVE_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bookings")
@Entity
public class Booking extends AbstractAuditing{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(20) default 'ACTIVE'")
    private ACTIVE_STATUS status;
    @Column
    @NotBlank
    @Size(max = 50)
    private String code;
    @Column
    @NotBlank
    private String booking_time;
    @Column
    @NotBlank
    private Date booking_date;
    @Column()
    @NotBlank
    private String description;

    @OneToOne(mappedBy = "booking")
    private Transaction transaction;

    @OneToOne(mappedBy = "booking")
    private Result result;

    @OneToOne
    @JoinColumn(name = "packageClinic_id", referencedColumnName = "id")
    private PackageClinic packageClinic;

    @OneToOne
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    private Doctors doctors;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
