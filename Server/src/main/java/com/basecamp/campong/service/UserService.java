package com.basecamp.campong.service;

import com.basecamp.campong.RequestDTO;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    // 회원 가입 서비스
    public boolean signup(User userBody) {
        User user = User.builder().email(userBody.getEmail())
                .password(userBody.getPassword()).phone(userBody.getPhone())
                .username(userBody.getUsername()).usernick(userBody.getUsernick()).build();

        var checkemail = checkemail(userBody);
        var checknick = checknick(userBody);

        if (checkemail || checknick) {
            return true;
        }

        user = userRepository.save(user);
        return false;
    }

    // 이메일 체크 서비스
    public boolean checkemail(User userBody) {
        int count = userRepository.countByEmail(userBody.getEmail());
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 닉네임 체크 서비스
    public boolean checknick(User userBody) {
        int count = userRepository.countByUsernick(userBody.getUsernick());
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 로그인 서비스
    public boolean login(User userBody, HttpSession session) {
        User user = userRepository.findByEmailAndPassword(userBody.getEmail(), userBody.getPassword());
        if (user == null) {
            return true;
        }
        //해당 user table에 세션 id 저장
        user.setSession(session.getId());
        //세션에 user 객체 저장
        session.setAttribute("user", user);
        return false;
    }


    // 닉네임 변경
    public boolean updatenick(long userID, User userBody) {
        User user = userRepository.findByUserid(userID);
        if (user == null) {
            return true;
        }
        user.setUsernick(userBody.getUsernick());
        return false;
    }

    // 세션, 쿠키로 유저 인증
    public long auth(HttpSession session, Cookie cookie, HttpServletResponse res) {
        // 세션 체크

        User user = (User) session.getAttribute("user");
        if (user != null) {
            return user.getUserid();
        }

        // 쿠키 체크
        if (cookie == null) {
            return -1;
        }

        user = userRepository.findBySession(cookie.getValue());
        if (user == null) {
            return -1;
        }
        
        //변경된 세션으로 쿠키 업데이트
        user.setSession(session.getId());
        cookie.setValue(session.getId());
        res.addCookie(cookie);

        return user.getUserid();
    }
}
