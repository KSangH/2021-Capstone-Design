package com.basecamp.campong.repository;

import com.basecamp.campong.domain.Category;
import com.basecamp.campong.domain.PostList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostList, Long> {

    //deletestate가 0인 post 목록 조회
    //Page<PostList> findAllByDeletestateOrderByPostidDesc(int state, Pageable pageable);

    //게시물 조회
    PostList findByPostid(long postid);

    //deletestate가 0인 post 목록 조회 + 카테고리, location, keyword 필터
    @Query("SELECT p FROM PostList p WHERE (:category is null or p.category = :category) " +
            "and p.deletestate = :state " +
            "and (:location is null or p.location = :location) "+
            "and (:keyword is null or p.title like %:keyword% or p.contents like %:keyword%) "+
            "order by p.postid desc ")
    Page<PostList> filteredPost(@Param("state") int state, @Param("category") Category category, @Param("location") String location, @Param("keyword") String keyword,Pageable pageable);
}
