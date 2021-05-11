package com.basecamp.campong.repository;

import com.basecamp.campong.domain.PostList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostList, Long> {

    //deletestate가 0인 post 목록 조회
    Page<PostList> findAllByDeletestateOrderByPostidDesc(int state, Pageable pageable);

    //게시물 조회
    PostList findByPostid(long postid);
}
