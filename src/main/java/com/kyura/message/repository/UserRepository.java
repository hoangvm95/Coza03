package com.kyura.message.repository;

import java.util.List;
import java.util.Optional;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.models.User;
import com.kyura.message.payload.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsernameAndStatus(String username, ACTIVE_STATUS status);

	Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);
	Boolean existsByUsernameAndIdNot(String username, Long id);
	Boolean existsByEmail(String email);
	Boolean existsByEmailAndIdNot(String email, Long id);
	Boolean existsByPhone(String phone);
	Boolean existsByPhoneAndIdNot(String phone, Long id);

	Page<User> findAllByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(String username, String email, String phone, Pageable pageable);

	Optional<User> findById(Long id);

	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER'")
	List<User> findAllUsersWithUserRole();

//	List<User> findByUsernameContainingOrEmailContainingOrPhoneContaining(String username, String email, String phone);
	@Query("SELECT DISTINCT u FROM User u WHERE u.username LIKE %:name% OR u.email LIKE %:name% OR u.phone LIKE %:name%")
	public List<User> findByUsernameContainingOrEmailContainingOrPhoneContaining(@Param("name") String name);
}

