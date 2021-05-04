package com.basecamp.campong.service;

import com.basecamp.campong.domain.Category;
import com.basecamp.campong.repository.CategoryRepository;
import com.basecamp.campong.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;



}
