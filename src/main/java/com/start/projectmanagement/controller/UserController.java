package com.start.projectmanagement.controller;

import com.start.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired UserService service;

    @GetMapping
    public Object getAll() {
        return service.getAll();
    }
}