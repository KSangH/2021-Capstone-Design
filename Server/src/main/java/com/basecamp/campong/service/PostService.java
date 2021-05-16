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

        Category category = null;

        if(catename != null){
            //카테고리 필터를 설정한 경우
            category = categoryRepository.findByCatename(catename);
            if(category == null){
                //존재하지 않는 카테고리를 요청한 경우
                return result.setError(2009, "존재하지 않는 카테고리입니다.");
            }
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

    //게시물 조회
    public JsonMap viewPost(long id, PostList post){
        System.out.println("viewPost START");
        JsonMap result = new JsonMap();

        //postid로 게시글 조회
        PostList viewPost = postRepository.findByPostid(post.getPostid());
        if( viewPost == null || viewPost.getDeletestate() == 1){
            //게시글을 조회하는 중에 작성자가 게시글을 삭제한 경우 or 존재하지 않는 postid를 넘겨준경우
            return result.setError(2007, "존재하지않는 게시글입니다.");
        }

        Post refinedPost = new Post(viewPost);
        result.put("post", refinedPost);

        //게시물을 조회하는 사람이 작성자인지 확인
        if(viewPost.getUser().getUserid() == id){
            result.put("mypost", true);
        }else{
            result.put("mypost", false);
        }

        System.out.println("viewPost END");
        return result;
    }

    //게시물 수정
    public JsonMap updatePost(long id, PostList post){
        System.out.println("updatePost START");
        JsonMap result = new JsonMap();

        //postid로 게시글 조회
        PostList viewPost = postRepository.findByPostid(post.getPostid());
        if(viewPost.getDeletestate() == 1 || viewPost == null){
            //게시글을 조회하는 중에 작성자가 게시글을 삭제한 경우 or 존재하지 않는 postid를 넘겨준경우
            return result.setError(2007, "존재하지않는 게시글입니다.");
        }


        viewPost.setLocation(post.getLocation());
        viewPost.setCategory(categoryRepository.findByCatename(post.getCatename()));
        viewPost.setTitle(post.getTitle());
        viewPost.setContents(post.getContents());
        viewPost.setFee(post.getFee());
        viewPost.setLat(post.getLat());
        viewPost.setLon(post.getLon());
        viewPost.setImageid(post.getImageid());

        System.out.println("updatePost END");
        return result;
    }
}
