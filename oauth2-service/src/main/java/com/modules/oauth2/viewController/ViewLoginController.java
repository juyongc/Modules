package com.modules.oauth2.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oauth")
public class ViewLoginController {

    @GetMapping("")
    public String login() {
        return "oauth";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
