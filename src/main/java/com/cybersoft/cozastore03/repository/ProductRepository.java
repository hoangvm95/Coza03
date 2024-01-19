package com.cybersoft.cozastore03.repository;

import com.cybersoft.cozastore03.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//JPA Buddy
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {
}
