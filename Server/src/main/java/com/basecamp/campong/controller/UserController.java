package com.basecamp.campong.controller;

import com.basecamp.campong.config.Config;
import com.basecamp.campong.util.JsonMap;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // 회원 가입
    @PostMapping(value = "/signup")
    public JsonMap signup(@RequestBody User userBody) {
        JsonMap result = new JsonMap();
        try {
            if (userService.signup(userBody)) {
                result.setError(1002, "이메일이나 닉네임이 중복되었습니다.");
            }
        } catch (UnexpectedRollbackException e) {
            result.setError(1001, "회원가입에 실패하였습니다.");
        } finally {
            return result;
        }
    }

    // 회원 가입 (이메일 중복 확인)
    @PostMapping(value = "/checkemail")
    public JsonMap checkemail(@RequestBody User userBody) {
        JsonMap result = new JsonMap();
        try {
            if (userService.checkemail(userBody)) {
                result.setError(1002, "이메일이 중복되었습니다.");
            }
        } catch (UnexpectedRollbackException e) {
            result.setError(1002, "잠시 후 다시 시도해주세요.");
        } finally {
            return result;
        }
    }

    // 회원 가입 (닉네임 중복 확인)
    @PostMapping(value = "/checknick")
    public JsonMap checknick(@RequestBody User userBody) {
        JsonMap result = new JsonMap();
        try {
            if (userService.checknick(userBody)) {
                result.setError(1003, "닉네임이 중복되었습니다.");
            }
        } catch (UnexpectedRollbackException e) {
            result.setError(1003, "잠시 후 다시 시도해주세요.");
        } finally {
            return result;
        }
    }

    // 로그인
    @PostMapping(value = "/login")
    public JsonMap login(@RequestBody User userBody,
                         HttpSession session, HttpServletResponse res) {
        JsonMap result = new JsonMap();
        try {
            return userService.login(userBody, session, res);
        } catch (UnexpectedRollbackException e) {
            return result.setError(1005, "잠시 후 다시 시도해주세요.");
        }
    }

    // 로그아웃
    @PostMapping(value = "/logout")
    public JsonMap logout(@CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                          HttpSession session, HttpServletResponse res) {
        JsonMap result = new JsonMap();
        try {
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }
            // 로그아웃 서비스 실행
            return userService.logout(id, session, res);

        } catch (UnexpectedRollbackException e) {
            return result.setError(1008, "잠시 후 다시 시도해주세요.");
        }
    }

    // 유저 정보
    @PostMapping(value = "/userinfo")
    public JsonMap userinfo(@CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                              HttpSession session, HttpServletResponse res) {

        JsonMap result = new JsonMap();
        try {
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }
            return userService.userinfo(id);
        } catch (UnexpectedRollbackException e) {
            return result.setError(1007, "잠시 후 다시 시도해주세요.");
        }
    }



    // 닉네임 변경
    @PostMapping(value = "/updateuser")
    public JsonMap updateuser(@RequestBody User userBody,
                              @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                              HttpSession session, HttpServletResponse res) {
        JsonMap result = new JsonMap();
        try {
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            return userService.updateuser(id, userBody);

        } catch (UnexpectedRollbackException e) {
            return result.setError(1007, "잠시 후 다시 시도해주세요.");
        }
    }

    //마이페이지
    @PostMapping(value="/mypage")
    public JsonMap mypage(@CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                          HttpSession session, HttpServletResponse res){
        JsonMap result = new JsonMap();
        try {
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }

            return userService.mypage(id);

        } catch (UnexpectedRollbackException e) {
            return result.setError(4001, "잠시 후 다시 시도해주세요.");
        }
    }
}