package com.kyura.message.repository;

import com.kyura.message.models.Specialists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialists,Integer>{
    List<Specialists> findAll();

    Specialists findById(Long Id);

    List<Specialists> findAllById(Long Id);


}
