package com.basecamp.campong.repository;

import com.basecamp.campong.domain.ReserveView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveReadRepository extends JpaRepository<ReserveView, Long> {

    long countAllByLenduseridAndState(long userid, int state);

    long countAllByBorrowuseridAndState(long userid, int state);

    Page<ReserveView> findAllByLenduseridAndStateBetweenOrderByReserveidDesc(long userid, int start, int end, Pageable pageable);

    Page<ReserveView> findAllByBorrowuseridAndStateBetweenOrderByReserveidDesc(long userid, int start, int end, Pageable pageable);
}
