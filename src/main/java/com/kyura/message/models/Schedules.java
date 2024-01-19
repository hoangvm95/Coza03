package com.kyura.message.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String schedules_date;

    @NotNull
    private LocalTime start_time;

    @NotNull
    private LocalTime end_time;

    @OneToOne
    @JoinColumn(name = "hospital_id",referencedColumnName = "id")
    @JsonIgnore
    private Hospitals hospitals;

    @OneToMany(mappedBy = "schedules")
    private Set<SchedulesDetail> listSchedulesDetail;

}
