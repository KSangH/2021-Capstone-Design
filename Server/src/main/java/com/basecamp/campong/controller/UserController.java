package com.basecamp.campong.controller;

import com.basecamp.campong.RequestDTO;
import com.basecamp.campong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입
    @PostMapping(value = "/signup")
    public HashMap<String, Object> signup(@RequestBody RequestDTO.UserBody userBody){
        return userService.signup(userBody);
    }

    // 회원 가입 (이메일 중복 확인)
    @PostMapping(value = "/checkemail")
    public HashMap<String, Object> checkemail(@RequestBody RequestDTO.UserBody userBody){
        return userService.checkemail(userBody);
    }

    // 회원 가입 (닉네임 중복 확인)
    @PostMapping(value = "/checknick")
    public HashMap<String, Object> checknick(@RequestBody RequestDTO.UserBody userBody){
        return userService.checknick(userBody);
    }

    @PostMapping(value = "/login")
    public HashMap<String, Object> login(@RequestBody RequestDTO.UserBody userBody){
        return userService.login(userBody);
    }

    @PostMapping(value = "/updatenick")
    public HashMap<String, Object> updatenick(@RequestBody RequestDTO.UserBody userBody){
        return userService.updatenick(userBody);
    }
}