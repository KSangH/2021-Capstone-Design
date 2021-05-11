package com.basecamp.campong.service;

import com.basecamp.campong.domain.Category;
import com.basecamp.campong.domain.Image;
import com.basecamp.campong.domain.PostList;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.repository.CategoryRepository;
import com.basecamp.campong.repository.ImageRepository;
import com.basecamp.campong.repository.PostRepository;
import com.basecamp.campong.repository.UserRepository;
import com.basecamp.campong.util.JsonMap;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    public JsonMap uploadPost(long id, PostList post){
        System.out.println("uploadPost START");
        JsonMap result = new JsonMap();

        //id로 user객체 받아서 post에 넣기
        User user = userRepository.findByUserid(id);
        post.setUser(user);

        //category 객체 받아서 post에 넣기
        Category category = categoryRepository.findByCatename(post.getCatename());
        post.setCategory(category);

        //image 객체 받아서 post에 넣기
        Image img = imageRepository.findByImageid(post.getImageid());
        if(img == null){
            //사용자가 게시물 등록 시 이미지를 등록하지 않은 경우
            post.setItemphoto(null);
        }else{
            post.setItemphoto(img);
        }

        if(user == null || category == null){
            System.out.println("uploadPost error");
            return result.setError(2003, "유효하지 않은 정보");
        }

        postRepository.save(post);
        System.out.println("uploadPost END");
        return result;

    }


}
