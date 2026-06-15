package com.seichigo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seichigo.domain.UserVo;
import com.seichigo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입 폼 요청 (GET)
    @GetMapping("/register")
    public String showRegisterForm() {
        return "member/register"; // templates/member/register.html
    }

    // 회원가입 처리 요청 (POST)
    @PostMapping("/register")
    public String register(UserVo userVo) {
        userService.membersave(userVo);
        return "redirect:/"; // 가입 성공 후 홈으로 리다이렉트
    }

    @GetMapping("/login")
    public String userLogin() {
        return "member/login";
    }
}
