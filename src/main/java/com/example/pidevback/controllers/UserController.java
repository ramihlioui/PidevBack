package com.example.pidevback.controllers;


import com.example.pidevback.entities.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {


    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello secured");
    }
}
