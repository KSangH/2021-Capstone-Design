package com.basecamp.campong.repository;

import com.basecamp.campong.domain.PostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReadRepository extends JpaRepository<PostView, Long> {
    Page<PostView> findAllByOrderByPostidDesc(Pageable pageable);
}
