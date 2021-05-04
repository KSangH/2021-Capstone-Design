package com.basecamp.campong.controller;

import com.basecamp.campong.config.Config;
import com.basecamp.campong.service.PostService;
import com.basecamp.campong.service.UserService;
import com.basecamp.campong.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

            //


        } catch (UnexpectedRollbackException e){
            result.setError(2001, "게시물 목록 조회 오류");
        } finally {
            return result;
        }
    }
}
