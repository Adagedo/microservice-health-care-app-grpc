package com.adagedo_softengineer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Patient {

    @Id
    @GeneratedValue
    private Integer id;

    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;
}
