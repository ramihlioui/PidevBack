package com.example.pidevback.controllers;


import com.example.pidevback.dto.UserDto;
import com.example.pidevback.entities.Users;
import com.example.pidevback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")

public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public Users findUser(@PathVariable(required = true , value = "id") Long id){
        return userService.findUserById(id);
    }

    @GetMapping("/all")
    public List<Users> findUser(){
        return userService.findUsers();
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody(required = true) UserDto userDto){
        userService.updateUser(userDto);
    }
}
