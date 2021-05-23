package com.basecamp.campong.controller;

import com.basecamp.campong.config.Config;
import com.basecamp.campong.domain.PostList;
import com.basecamp.campong.service.PostService;
import com.basecamp.campong.service.UserService;
import com.basecamp.campong.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
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
    //게시물목록조회(2) - 필터링
    @GetMapping(value = "list")
    public JsonMap readList(@RequestParam(value = "pagenum") int pagenum,
                            @RequestParam(value = "catename", required = false) String catename,
                            @RequestParam(value = "location", required = false) String location,
                            @RequestParam(value = "keyword", required = false) String keyword,
            @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                            HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try{
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            //게시물목록조회
            return postService.readList(pagenum, catename, location, keyword);

        } catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
            return result.setError(2001, "게시물 목록 조회 오류(" + e.getLocalizedMessage() + ")");
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

        } catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
            return result.setError(2002, "게시물 등록 오류(" + e.getLocalizedMessage() + ")");
        }
    }

    //게시물 삭제
    @PostMapping(value = "delete")
    public JsonMap deletePost(@RequestBody PostList post,
                              @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                              HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try{
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            //게시물삭제 서비스 실행
            return postService.deletePost(post.getPostid());

        } catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
            return result.setError(2004, "게시물 삭제 오류(" + e.getLocalizedMessage() + ")");
        }

    }

    //게시물 조회
    @PostMapping(value = "view")
    public JsonMap viewPost(@RequestBody PostList post,
                              @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                              HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try{
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            //게시물조회 서비스 실행
            return postService.viewPost(id,post);

        } catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
            return result.setError(2006, "게시물 조회 오류(" + e.getLocalizedMessage() + ")");
        }

    }
    //게시물 수정
    @PostMapping(value = "update")
    public JsonMap updatePost(@RequestBody PostList post,
                            @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                            HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try{
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            //게시물수정 서비스 실행
            return postService.updatePost(id,post);

        } catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
            return result.setError(2008, "게시물 수정 오류(" + e.getLocalizedMessage() + ")");
        }

    }

    //특정 사용자의 게시글 목록 조회
    @GetMapping(value = "list/{usernick}")
    public JsonMap readListByUser(@RequestParam(value = "pagenum") int pagenum,
                                    @PathVariable String usernick,
                                  @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                                  HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try{
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }
            
            //특정 유저의 게시물 목록을 조회하는 서비스
            return postService.readListByUser(usernick, pagenum);

        } catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
            return result.setError(2010, "특정 사용자의 게시글 목록 조회 오류(" + e.getLocalizedMessage() + ")");
        }


    }
    /*
    //내 게시글 목록 조회
    @GetMapping(value = "list/mypost")
    public JsonMap mypostList(@CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                              HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try{
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            //내 게시글 목록 조회 서비스
            return postService.mypostList(id);

        } catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
            return result.setError(2011, "내 게시글 목록 조회 오류(" + e.getLocalizedMessage() + ")");
        }
    }

 */
}
