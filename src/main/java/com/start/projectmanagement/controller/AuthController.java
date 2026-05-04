package com.start.projectmanagement.controller;

import com.start.projectmanagement.dto.*;
import com.start.projectmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired AuthService service;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest r) {
        return service.register(r);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest r) {
        return service.login(r);
    }
}