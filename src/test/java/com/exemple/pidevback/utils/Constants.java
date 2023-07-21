package com.exemple.pidevback.utils;

import com.example.pidevback.entities.Users;

public class Constants {


    public static Users createUser(){
        Users a = new Users();
        a.setId(9L);
        a.setEmail("ramihlioui.tsuna@gmail.com");
        a.setPassword("kzefmlbf");
        a.setFullName("ramihlioui");
        a.setIsEnabled(false);
        return a;
    }
}
