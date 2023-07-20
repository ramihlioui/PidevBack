package com.example.pidevback;

import com.example.pidevback.entities.Reclamation;
import com.example.pidevback.entities.Users;

import java.util.ArrayList;
import java.util.Date;

public class TestUtils {
    public static final long RECLAMATION_ID = 10L;
    public static final long USER_ID = 10L;
    public static final Users USER = new Users(USER_ID,"name","mail","pass",1,true,false,null,null,new ArrayList<>());
    public static  Reclamation RECLAMATION= new Reclamation(RECLAMATION_ID,"test complaint","described",false,"",new Date(),USER);
}
