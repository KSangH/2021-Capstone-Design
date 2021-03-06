package com.basecamp.campong.repository;

import com.basecamp.campong.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    //id로 검색
    Image findByImageid(long imageid);
}
