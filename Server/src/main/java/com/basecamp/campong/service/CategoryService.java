package com.basecamp.campong.service;

import com.basecamp.campong.domain.Category;
import com.basecamp.campong.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category makeCategory(String name){
        Category category = Category.builder().catename(name).build();
        category = repository.save(category);
        return category;
    }

}
