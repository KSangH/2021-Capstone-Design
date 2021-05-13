package com.basecamp.campong.service;

import com.basecamp.campong.domain.*;
import com.basecamp.campong.repository.CategoryRepository;
import com.basecamp.campong.repository.ImageRepository;
import com.basecamp.campong.repository.PostRepository;
import com.basecamp.campong.repository.UserRepository;
import com.basecamp.campong.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //게시물등록
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
        //데이터베이스 저장
        post =  postRepository.save(post);
        System.out.println("uploadPost END");
        result.put("postid", post.getPostid());
        return result;

    }

    /**
     * 게시물목록조회(1) - 기본 : 예약가능한 장비만
     * 게시물목록조회(2) - 필터링
     *
     * @param pagenum 시작 page
     * @param catename 카테고리명
     * @param location 장소
     * @param keyword 키워드
     * @return 조회된 post list
     */
    public JsonMap readList(int pagenum, String catename, String location, String keyword){
        System.out.println("readList START");
        JsonMap result = new JsonMap();

        //List<PostList> postLists = postRepository.findAllByDeletestateOrderByPostidDesc(0, PageRequest.of(pagenum,10)).getContent();

        Category category = new Category();

        if(catename != null){
            //카테고리 필터를 설정한 경우
            category = categoryRepository.findByCatename(catename);
        }else{
            //카테고리 필터를 설정하지 않은 경우
            category = null;
        }

        List<PostList> postLists = postRepository.filteredPost(0,category,location,keyword, PageRequest.of(pagenum, 10)).getContent();


        // 사이즈 입력
        result.put("num", postLists.size());

        // 보낼 데이터 편집
        ArrayList<Post> postArrayList = new ArrayList<>();
        for(PostList post : postLists){
            postArrayList.add(new Post(post));
        }
        result.put("data", postArrayList);

        System.out.println("readList END");
        return result;
    }

    //게시물 삭제
    public JsonMap deletePost(long postid){
        System.out.println("deletePost START");
        JsonMap result = new JsonMap();

        //postid로 삭제할 게시글 조회
        PostList post = postRepository.findByPostid(postid);
        if(post == null){
            //삭제할 게시글이 없는 경우
            return result.setError(2005, "해당 게시물이 존재하지 않습니다.");
        }
        post.setDeletestate((int) 1);
        post.setDeletedate(LocalDateTime.now());

        System.out.println("deletePost END");
        return result;
    }
}
