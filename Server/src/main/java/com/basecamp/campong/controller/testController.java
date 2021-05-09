package com.basecamp.campong.controller;

import com.basecamp.campong.config.Config;
import com.basecamp.campong.domain.Image;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.service.ImageService;
import com.basecamp.campong.service.UserService;
import com.basecamp.campong.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/test")
public class testController {

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    // 회원 가입
    @PostMapping(value = "/signup")
    public void signup(@ModelAttribute final User userBody) {

        JsonMap result = new JsonMap();
        try {
            if (userService.signup(userBody)) {
                result.setError(1002, "이메일이나 닉네임이 중복되었습니다.");
            }
        } catch (UnexpectedRollbackException e) {
            result.setError(1001, "회원가입에 실패하였습니다.");
        } finally {
            System.out.println(result);
        }

    }

    //로그인
    @PostMapping(value = "/login")
    public void login(@ModelAttribute final User userBody,
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
            System.out.println(result);
        }
    }

    // 이미지 업로드
    @PostMapping(value = "/image/upload")
    public void imageUpload(@RequestParam(value = "image") MultipartFile file,
                               @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                               HttpSession session, HttpServletResponse res) {
        JsonMap result = new JsonMap();
        try {
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                System.out.println(result.setAuthFailed());
            }
            result.setMessage(imageService.imageUpload(file)+"");
        } catch (UnexpectedRollbackException | IOException e) {
            result.setError(3001, "이미지 업로드에 실패하였습니다.");
        } finally {
            System.out.println(result);
        }
    }

    // 이미지 다운로드
    @GetMapping (value = "/{id}")
    public ResponseEntity<byte[]> imageDownload(@PathVariable long id) {

        try {
            Image image = imageService.getImageFile(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\""+id+"\"")
                    .header(HttpHeaders.CONTENT_TYPE, image.getMediatype())
                    .body(image.getImagefile());
        } catch (UnexpectedRollbackException e) {
            return null;
        }
    }
}
