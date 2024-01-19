package com.kyura.message.repository;

import com.kyura.message.models.CompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositeRepository extends JpaRepository<CompositeId,Integer> {

}
