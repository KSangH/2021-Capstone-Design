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

    // 회원 가입 서비스
    public HashMap<String, Object> signup(RequestDTO.UserBody userBody){
        var result = new HashMap<String, Object>();
        User user = User.builder().email(userBody.getEmail())
                .userpassword(userBody.getPassword()).phone(userBody.getPhone())
                .username(userBody.getUsername()).usernick(userBody.getUsernick()).build();

        var checkemail = checkemail(userBody);
        if ((Boolean)checkemail.get("error")) {
            return checkemail;
        }

        var checknick = checknick(userBody);
        if ((Boolean)checknick.get("error")) {
            return checknick;
        }

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

    // 이메일 체크 서비스
    public HashMap<String, Object> checkemail(RequestDTO.UserBody userBody){
        var result = new HashMap<String, Object>();
        try {
            User user = userRepository.findByEmail(userBody.getEmail());
            if(user != null){
                result.put("error", true);
                result.put("errorcode", 1002);
                result.put("message", "중복 된 이메일입니다.");
            } else {
                result.put("error", false);
            }
        }catch(Exception e){
            result.put("error", true);
            result.put("errorcode", 1002);
            result.put("message", "잠시 후 다시 시도해주세요.");
        }
        return result;
    }

    // 닉네임 체크 서비스
    public HashMap<String, Object> checknick(RequestDTO.UserBody userBody){
        var result = new HashMap<String, Object>();
        try {
            User user = userRepository.findByUsernick(userBody.getUsernick());
            if(user != null){
                result.put("error", true);
                result.put("errorcode", 1002);
                result.put("message", "중복 된 닉네임입니다.");
            } else {
                result.put("error", false);
            }
        }catch(Exception e){
            result.put("error", true);
            result.put("errorcode", 1002);
            result.put("message", "잠시 후 다시 시도해주세요.");
        }
        return result;
    }


    public HashMap<String, Object> login(RequestDTO.UserBody userBody){
        var result = new HashMap<String, Object>();

        return result;
    }
}
