package com.basecamp.campong.controller;

import com.basecamp.campong.config.Config;
import com.basecamp.campong.domain.Reservelist;
import com.basecamp.campong.service.ReserveService;
import com.basecamp.campong.service.UserService;
import com.basecamp.campong.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Autowired
    UserService userService;

    @Autowired
    ReserveService reserveService;

    // 예약버튼을 누르면 예약이 마감되었는지 처음 확인하는 단계
    @PostMapping(value = "/init")
    public JsonMap reserveinit(@RequestBody Reservelist body,
                               @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                               HttpSession session, HttpServletResponse res) {
        JsonMap result = new JsonMap();
        try {
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }
            return reserveService.reserveinit(body);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return result.setError(1007, "잠시 후 다시 시도해주세요.(" + e.getLocalizedMessage() + ")");
        }
    }

    @PostMapping(value = "/request")
    public JsonMap reserveRequest(@RequestBody Reservelist body,
                                  @CookieValue(value = Config.COOKIE_SESSIONID, required = false) Cookie cookie,
                                  HttpSession session, HttpServletResponse res) {
        JsonMap result = new JsonMap();
        try {
            long id = userService.auth(session, cookie, res);
            if (id < 0) {
                return result.setAuthFailed();
            }
            return reserveService.reserveRequest(id, body);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return result.setError(1007, "잠시 후 다시 시도해주세요.(" + e.getLocalizedMessage() + ")");
        }
    }
}
