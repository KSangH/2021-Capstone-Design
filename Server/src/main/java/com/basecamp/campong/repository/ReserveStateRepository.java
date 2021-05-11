package com.basecamp.campong.repository;

import com.basecamp.campong.domain.ReserveState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveStateRepository extends JpaRepository<ReserveState, Long> {
}
