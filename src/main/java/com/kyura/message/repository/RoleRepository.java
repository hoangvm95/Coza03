package com.kyura.message.repository;

import java.util.Optional;

import com.kyura.message.common.ROLE;
import com.kyura.message.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ROLE name);
}
