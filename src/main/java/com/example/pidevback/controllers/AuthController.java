package com.example.pidevback.controllers;


import com.example.pidevback.dto.AuthenticationRequest;
import com.example.pidevback.dto.AuthenticationResponse;
import com.example.pidevback.dto.UserDto;
import com.example.pidevback.entities.Role;
import com.example.pidevback.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

   @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody UserDto userDto) throws Exception {
        log.info("arararara");
        userService.signIn(userDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        log.info("arararara");

        return ResponseEntity.ok(userService.logIn(request));
    }


}
