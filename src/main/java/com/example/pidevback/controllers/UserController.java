package com.example.pidevback.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pidevback.entities.Users;
import com.example.pidevback.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public List<Users> getAllUsers(){
        return userService.findAllUsers();
    }
}
