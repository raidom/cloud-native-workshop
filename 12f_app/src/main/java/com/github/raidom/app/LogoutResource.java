package com.github.raidom.app;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
public class LogoutResource {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {

        session.invalidate();
        return "Logout done!";
    }
}
