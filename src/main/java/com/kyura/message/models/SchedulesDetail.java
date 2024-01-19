package com.kyura.message.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kyura.message.common.ACTIVE_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules_detail")
public class SchedulesDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String schedules_date;

    private LocalTime start_time;

    private LocalTime end_time;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(20) default 'ACTIVE'")
    private ACTIVE_STATUS status;
    @ManyToOne
    @JoinColumn(name="schedules_id")
    @JsonIgnore
    private Schedules schedules;
}
