package com.basecamp.campong.controller;

import com.basecamp.campong.config.Config;
import com.basecamp.campong.domain.PostList;
import com.basecamp.campong.service.PostService;
import com.basecamp.campong.service.UserService;
import com.basecamp.campong.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    //게시물목록조회(1) - 기본 : 예약가능한 장비만
    //미완
    @GetMapping(value = "list")
    public JsonMap readList(@CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                            HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try{
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            //게시물목록조회
            


        } catch (UnexpectedRollbackException e){
            result.setError(2001, "게시물 목록 조회 오류");
        } finally {
            return result;
        }
    }

    //게시물등록
    @PostMapping(value = "upload")
    public JsonMap postUpload(@RequestBody PostList post,
                            @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                            HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try{
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            //게시물등록 서비스 실행
            return postService.uploadPost(id,post);

        } catch (UnexpectedRollbackException e){
            return result.setError(2002, "게시물 등록 오류");
        }

    }
}
