package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.entities.User;

@Controller
public class UserController {

    @GetMapping("user")
    public String showUserInfo(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "users/user-page";
    }
}
