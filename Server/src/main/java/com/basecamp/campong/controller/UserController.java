package com.basecamp.campong.controller;

import com.basecamp.campong.config.Config;
import com.basecamp.campong.util.JsonMap;
import com.basecamp.campong.RequestDTO;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

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
            if (userService.login(userBody, session)) {
                result.setError(1005, "아이디랑 비밀번호를 확인해주세요");
            }

            // 쿠키 생성
            Cookie cookie = new Cookie(Config.COOKIE_SESSIONID, session.getId());
            cookie.setMaxAge(Config.COOKIE_MAXAGE);
            res.addCookie(cookie);

        } catch (UnexpectedRollbackException e) {
            result.setError(1005, "잠시 후 다시 시도해주세요.");
        } finally {
            return result;
        }
    }

    // 닉네임 변경
    @PostMapping(value = "/updatenick")
    public JsonMap updatenick(@RequestBody User userBody,
                              @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                              HttpSession session, HttpServletResponse res) {
        JsonMap result = new JsonMap();
        try {
            // 사용자 인증
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }
            //닉네임 변경
            userService.updatenick(id, userBody);
        } catch (UnexpectedRollbackException e) {
            result.setError(1007, "잠시 후 다시 시도해주세요.");
        } finally {
            return result;
        }
    }
}