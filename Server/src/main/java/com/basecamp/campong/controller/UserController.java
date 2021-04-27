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

    @PostMapping(value = "/signup")
    public HashMap<String, Object> signup(@RequestBody RequestDTO.UserBody userBody){
        return userService.signup(userBody);
    }


    @PostMapping("/login")
    public String login(){
        return "로그인";
    }
}