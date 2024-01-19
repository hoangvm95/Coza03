package com.kyura.message.repository;

import com.kyura.message.common.ACTIVE_STATUS;
import com.kyura.message.models.ResetPassHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResetPassHistoryRepository extends JpaRepository<ResetPassHistory, Long> {

    List<ResetPassHistory> findByUserId(long userId);

    Optional<ResetPassHistory> findOneByActiveCodeAndStatus(String activeCode, ACTIVE_STATUS status);

    Optional<ResetPassHistory> findOneByActiveCodeAndStatusAndUserId(String activeCode, ACTIVE_STATUS status, long userId);
}
