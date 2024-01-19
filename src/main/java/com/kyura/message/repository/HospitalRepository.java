package com.kyura.message.repository;

import com.kyura.message.models.Hospitals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospitals,Long> {
    List<Hospitals> findAll();
}
