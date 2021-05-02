package com.basecamp.campong.controller;

import com.basecamp.campong.util.JsonMap;
import com.basecamp.campong.RequestDTO;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            if(userService.signup(userBody)){
                result.setError(1000, "이메일이나 닉네임이 중복되었습니다.");
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
            if(userService.checkemail(userBody)){
                result.setError(1000, "이메일이 중복되었습니다.");
            }
        } catch (UnexpectedRollbackException e) {
            result.setError(1001, "잠시 후 다시 시도해주세요.");
        } finally {
            return result;
        }
    }

    // 회원 가입 (닉네임 중복 확인)
    @PostMapping(value = "/checknick")
    public JsonMap checknick(@RequestBody User userBody) {
        JsonMap result = new JsonMap();
        try {
            if(userService.checknick(userBody)){
                result.setError(1000, "닉네임이 중복되었습니다.");
            }
        } catch (UnexpectedRollbackException e) {
            result.setError(1001, "잠시 후 다시 시도해주세요.");
        } finally {
            return result;
        }
    }

    @PostMapping(value = "/login")
    public HashMap<String, Object> login(@RequestBody RequestDTO.UserBody userBody) {
        return userService.login(userBody);
    }

    @PostMapping(value = "/updatenick")
    public HashMap<String, Object> updatenick(@RequestBody RequestDTO.UserBody userBody) {
        return userService.updatenick(userBody);

    }
}