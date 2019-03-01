package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zmysna
 * @Version 1.0
 * @Date 2019/2/28 9:49
 */
@RestController
public class LoginController {
    /** 获得登录用户名*/
    @GetMapping("/showLoginName")
    public String showLoginName() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return username;
    }

}
