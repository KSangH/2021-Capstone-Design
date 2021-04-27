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
    public HashMap<String, Object> signup(RequestDTO.UserBody userBody) {
        var result = new HashMap<String, Object>();
        User user = User.builder().email(userBody.getEmail())
                .userpassword(userBody.getPassword()).phone(userBody.getPhone())
                .username(userBody.getUsername()).usernick(userBody.getUsernick()).build();

        var checkemail = checkemail(userBody);
        if ((Boolean) checkemail.get("error")) {
            return checkemail;
        }

        var checknick = checknick(userBody);
        if ((Boolean) checknick.get("error")) {
            return checknick;
        }

        try {
            userRepository.save(user);
            result.put("error", false);
        } catch (Exception e) {
            result.put("error", true);
            result.put("errorcode", 1001);
            result.put("message", "회원가입에 실패하였습니다.");
        }
        return result;
    }

    // 이메일 체크 서비스
    public HashMap<String, Object> checkemail(RequestDTO.UserBody userBody) {
        var result = new HashMap<String, Object>();
        try {
            int count = userRepository.countByEmail(userBody.getEmail());
            if (count == 0) {
                result.put("error", false);
            } else {
                result.put("error", true);
                result.put("errorcode", 1002);
                result.put("message", "중복 된 이메일입니다.");
            }
        } catch (Exception e) {
            result.put("error", true);
            result.put("errorcode", 1002);
            result.put("message", "잠시 후 다시 시도해주세요.");
        }
        return result;
    }

    // 닉네임 체크 서비스
    public HashMap<String, Object> checknick(RequestDTO.UserBody userBody) {
        var result = new HashMap<String, Object>();
        try {
            int count = userRepository.countByUsernick(userBody.getUsernick());
            if (count == 0) {
                result.put("error", false);
            } else {
                result.put("error", true);
                result.put("errorcode", 1002);
                result.put("message", "중복 된 닉네임입니다.");
            }
        } catch (Exception e) {
            result.put("error", true);
            result.put("errorcode", 1002);
            result.put("message", "잠시 후 다시 시도해주세요.");
        }
        return result;
    }

    // 로그인 서비스
    public HashMap<String, Object> login(RequestDTO.UserBody userBody) {
        var result = new HashMap<String, Object>();
        try {
            int count = userRepository.countByEmailAndUserpassword(userBody.getEmail(), userBody.getPassword());
            if (count > 0) {
                result.put("error", false);
                // 아래는 세션 관련 정보가 추가되어야 합니다.
                result.put("session", "정보");
            } else {
                result.put("error", true);
                result.put("errorcode", 1002);
                result.put("message", "아이디와 비밀번호를 확인해주세요.");
            }
        } catch (Exception e) {
            result.put("error", true);
            result.put("errorcode", 1002);
            result.put("message", "잠시 후 다시 시도해주세요.");
        }
        return result;
    }

    // 닉네임 변경
    public HashMap<String, Object> updatenick(RequestDTO.UserBody userBody) {
        var result = new HashMap<String, Object>();
        try {
            // 세션이나 토큰 방식으로 본인인증 되어 조회한다. 아래는 수정이 필요합니다.
            User user = userRepository.findByUserid(1);
            if(user != null){
                user.setUsernick(userBody.getUsernick());
                userRepository.save(user);
            }

        } catch (Exception e) {
            result.put("error", true);
            result.put("errorcode", 1002);
            result.put("message", "잠시 후 다시 시도해주세요.");
        }
        return result;
    }
}
