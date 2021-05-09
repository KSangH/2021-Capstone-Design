package com.basecamp.campong.repository;

import com.basecamp.campong.domain.Category;
import com.basecamp.campong.domain.PostList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCatename(String name);
}
