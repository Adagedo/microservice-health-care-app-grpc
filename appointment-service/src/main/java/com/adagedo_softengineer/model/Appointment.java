package com.adagedo_softengineer.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Appointment {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer doctor_id;

    private Integer patient_id;

    private String patient_name;

    private String doctor_name;

    private LocalDate appointment_date;

    private LocalTime appointment_time;

    private String purpose;
    
}