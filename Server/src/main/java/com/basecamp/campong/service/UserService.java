package com.basecamp.campong.service;

import com.basecamp.campong.RequestDTO;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public HashMap<String, Object> signup(RequestDTO.UserBody userBody){
        var result = new HashMap<String, Object>();
        User user = User.builder().email(userBody.getEmail())
                .userpassword(userBody.getPassword()).phone(userBody.getPhone())
                .username(userBody.getUsername()).usernick(userBody.getUsernick()).build();
        try {
            userRepository.save(user);
            result.put("error", false);
        }catch(Exception e){
            result.put("error", true);
            result.put("errorcode", 1001);
            result.put("message", "회원가입에 실패하였습니다.");
        }
        return result;
    }


    public HashMap<String, Object> login(RequestDTO.UserBody userBody){
        var result = new HashMap<String, Object>();

        return result;
    }
}
