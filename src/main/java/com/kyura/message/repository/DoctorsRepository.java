package com.kyura.message.repository;

import com.kyura.message.models.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors,Integer> {
    List<Doctors> findAll();

    @Query("SELECT d FROM Doctors d JOIN d.specialityId s WHERE s.id = :specialtyId")
    List<Doctors> findBySpecialityId_Id(@Param("specialtyId")  Long specialityId);

    Optional<Doctors> findById(Long Id);

}
