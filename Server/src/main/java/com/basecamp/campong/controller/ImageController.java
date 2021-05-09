package com.basecamp.campong.controller;

import com.basecamp.campong.config.Config;
import com.basecamp.campong.domain.Image;
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

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    // 이미지 업로드
    @PostMapping(value = "/upload")
    public JsonMap imageUpload(@RequestParam(value = "image") MultipartFile file,
                               @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                               HttpSession session, HttpServletResponse res) {
        JsonMap result = new JsonMap();
        try {
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }
            result.put("imageid", imageService.imageUpload(file));
        } catch (UnexpectedRollbackException e) {
            result.setError(3001, "이미지 업로드에 실패하였습니다.");
        } finally {
            return result;
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
