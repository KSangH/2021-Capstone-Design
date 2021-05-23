package com.basecamp.campong.repository;

import com.basecamp.campong.domain.ReserveState;
import com.basecamp.campong.domain.ReserveList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReserveStateRepository extends JpaRepository<ReserveState, Long> {

    Optional<ReserveState> findTopByReserveOrderByStateidDesc(ReserveList reservelist);

}
