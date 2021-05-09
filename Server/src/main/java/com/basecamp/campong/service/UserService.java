package com.basecamp.campong.service;

import com.basecamp.campong.RequestDTO;
import com.basecamp.campong.config.Config;
import com.basecamp.campong.domain.Image;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.repository.ImageRepository;
import com.basecamp.campong.repository.UserRepository;
import com.basecamp.campong.util.JsonMap;
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

    @Autowired
    ImageRepository imageRepository;

    // 회원 가입 서비스
    public boolean signup(User userBody) {
        System.out.println("회원가입# 시작하였습니다.");
        User user = User.builder().email(userBody.getEmail())
                .password(userBody.getPassword()).phone(userBody.getPhone())
                .username(userBody.getUsername()).usernick(userBody.getUsernick()).build();

        var checkemail = checkemail(userBody);
        var checknick = checknick(userBody);


        if (checkemail || checknick) {
            System.out.println("회원가입# 중복발생 하였습니다.");
            return true;
        }

        user = userRepository.save(user);
        System.out.println("회원가입# 종료하였습니다.");
        return false;
    }

    // 이메일 체크 서비스
    public boolean checkemail(User userBody) {
        System.out.println("이메일중복# 시작하였습니다.");
        int count = userRepository.countByEmail(userBody.getEmail());
        System.out.println("이메일중복# 종료하였습니다.");
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 닉네임 체크 서비스
    public boolean checknick(User userBody) {
        System.out.println("닉네임중복# 시작하였습니다.");
        int count = userRepository.countByUsernick(userBody.getUsernick());
        System.out.println("닉네임중복# 종료하였습니다.");
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 로그인 서비스
    public JsonMap login(User userBody, HttpSession session, HttpServletResponse res) {
        System.out.println("로그인# 시작하였습니다.");
        JsonMap result = new JsonMap();

        User user = userRepository.findByEmailAndPassword(userBody.getEmail(), userBody.getPassword());
        if (user == null) {
            System.out.println("로그인# 실패하였습니다. 틀림");
            return result.setError(1005, "아이디랑 비밀번호를 확인해주세요");
        }

        // 쿠키 생성
        Cookie cookie = new Cookie(Config.COOKIE_SESSIONID, session.getId());
        cookie.setMaxAge(Config.COOKIE_MAXAGE);
        res.addCookie(cookie);

        //해당 user table에 세션 id 저장
        user.setSession(session.getId());

        //세션에 user 객체 저장
        session.setAttribute("user", user);
        System.out.println("로그인# 성공하였습니다.");
        return result;
    }

    // 로그아웃 서비스
    public JsonMap logout(long id, HttpSession session, HttpServletResponse res) {
        System.out.println("로그아웃# 시작하였습니다.");
        JsonMap result = new JsonMap();

        User user = userRepository.findByUserid(id);

        if (user == null) {
            System.out.println("로그아웃# 실패하였습니다. 유저없음");
            return result.setError(1009, "유저 정보가 없습니다.");
        }

        // 세션 초기화
        session.invalidate();

        // 쿠키 삭제
        Cookie cookie = new Cookie(Config.COOKIE_SESSIONID, "");
        cookie.setMaxAge(Config.COOKIE_MAXAGE);
        res.addCookie(cookie);

        //해당 user table에 세션 초기화
        user.setSession(null);

        System.out.println("로그아웃# 성공하였습니다.");
        return result;
    }


    // 닉네임 변경
    public JsonMap userinfo(long userID) {
        System.out.println("유저정보# 시작하였습니다.");
        JsonMap result = new JsonMap();
        User user = userRepository.findByUserid(userID);
        if (user == null) {
            System.out.println("유저정보# 유저가 없습니다.");
            return result.setError(1009, "유저 정보가 없습니다.");
        }
        result.put("usernick", user.getUsernick());
        Image image = user.getProfile();
        result.put("imageid", image == null ? null : image.getImageid());
        System.out.println("유저정보# 종료되었습니다.");
        return result;
    }


    // 닉네임 변경
    public JsonMap updateuser(long userID, User userBody) {
        System.out.println("유저정보업데이트# 시작하였습니다.");
        JsonMap result = new JsonMap();
        User user = userRepository.findByUserid(userID);
        if (user == null) {
            System.out.println("유저정보업데이트# 실패하였습니다. 없음");
            return result.setError(1009, "유저 정보가 없습니다.");
        }
        user.setUsernick(userBody.getUsernick());
        user.setProfile(imageRepository.findById(userBody.getImageid()).orElse(null));
        System.out.println("유저정보업데이트# 성공하였습니다.");
        return result;
    }

    // 세션, 쿠키로 유저 인증
    public long auth(HttpSession session, Cookie cookie, HttpServletResponse res) {
        // 세션 체크
        System.out.println("사용자인증# 시작하였습니다.");
        User user = (User) session.getAttribute("user");
        if (user != null) {
            System.out.println("사용자인증# 세션에서 성공하였습니다");
            return user.getUserid();
        }

        // 쿠키 체크
        if (cookie == null) {
            System.out.println("사용자인증# 실패하였습니다(1)");
            return -1;
        }

        user = userRepository.findBySession(cookie.getValue());
        if (user == null) {
            System.out.println("사용자인증# 실패하였습니다(2)");
            return -1;
        }
        
        //변경된 세션으로 쿠키 업데이트
        user.setSession(session.getId());
        cookie.setValue(session.getId());
        res.addCookie(cookie);
        System.out.println("사용자인증# 성공하였습니다");
        return user.getUserid();
    }
}
