package com.example.pidevback.controllers;


import com.example.pidevback.entities.Users;
import com.example.pidevback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
