package com.basecamp.campong.repository;

import com.basecamp.campong.domain.Category;
import com.basecamp.campong.domain.PostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReadRepository extends JpaRepository<PostView, Long> {
    @Query("SELECT p FROM PostView p WHERE (:category is null or p.catename = :category) " +
            "and (:location is null or p.location = :location) "+
            "and (:keyword is null or p.title like %:keyword% or p.contents like %:keyword%) "+
            "order by p.postid desc ")
    Page<PostView> filteredPost(@Param("category") String category, @Param("location") String location, @Param("keyword") String keyword, Pageable pageable);

    //usernick으로 게시글 목록조회
    Page<PostView> findAllByUsernick(String usernick, Pageable pageable);

    //userid로 게시글 목록 조회
}
