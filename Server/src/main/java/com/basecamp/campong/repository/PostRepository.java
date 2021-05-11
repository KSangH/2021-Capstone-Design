package com.basecamp.campong.repository;

import com.basecamp.campong.domain.PostList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostList, Long> {

    //deletestate가 0인 post 목록 조회
    List<PostList> findAllByDeletestateOrderByUploaddate(int state);
}
