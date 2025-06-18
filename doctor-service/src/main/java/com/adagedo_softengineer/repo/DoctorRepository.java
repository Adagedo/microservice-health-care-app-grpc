package com.adagedo_softengineer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adagedo_softengineer.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{

}
