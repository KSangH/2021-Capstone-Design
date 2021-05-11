package com.basecamp.campong.repository;

import com.basecamp.campong.domain.PostList;
import com.basecamp.campong.domain.Reservelist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<Reservelist, Long> {

    Optional<Reservelist> findByPost(PostList postList);
}
