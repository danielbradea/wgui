package com.bid90.wgui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @RequestMapping(value = "/403")
    public String error403() {
        return "403";
    }


}
