package com.example.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class Controller_1 {

    @GetMapping("home_page")
    public String getHomePage() {
        return "HOME PAGE";
    }

    @GetMapping("page_for_users")
    public String pageForUsers() {
        return "PAGE FOR USERS";
    }

    @GetMapping("page_for_admins")
    public String pageForAdmins() {
        return "PAGE FOR ADMINS";
    }

    @GetMapping("read_secret")
    public String readSecret() {
        return "READ SECRET";
    }
}