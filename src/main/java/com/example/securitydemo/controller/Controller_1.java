package com.example.securitydemo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class Controller_1 {

    @GetMapping("/homePage")
    public String getHomePage(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "HOME PAGE " + principal.getName();
    }
}
