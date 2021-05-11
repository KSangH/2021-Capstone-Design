package com.basecamp.campong.repository;

import com.basecamp.campong.domain.PostList;
import com.basecamp.campong.domain.Reservelist;
import com.basecamp.campong.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<Reservelist, Long> {

    Optional<Reservelist> findByPost(PostList postList);

    Page<Reservelist> findAllByUserOrderByReserveidDesc(User user, Pageable pageable);
}
