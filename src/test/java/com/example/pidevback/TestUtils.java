package com.example.pidevback;

import com.example.pidevback.entities.Reclamation;
import com.example.pidevback.entities.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtils {
    public static final long RECLAMATION_ID_TO_CLOSE = 1L;
    public static final long RECLAMATION_ID = 10L;
    public static final long USER_ID = 10L;
    public static final Users USER = new Users(USER_ID,"name","mail","pass",1,true,false,null,null,new ArrayList<>());
    public static final Reclamation RECLAMATION_TO_CLOSE= new Reclamation(RECLAMATION_ID_TO_CLOSE,"test complaint","described",false,"",new Date(),USER);
    public static final Reclamation RECLAMATION_OPEN= new Reclamation(RECLAMATION_ID,"test complaint","described",false,"",new Date(),USER);
    public static final Reclamation RECLAMATION_CLOSED= new Reclamation(RECLAMATION_ID+1,"test complaint2","described",true,"fixed",new Date(),USER);
    public static final List<Reclamation> ALL = new ArrayList<Reclamation>(List.of(RECLAMATION_OPEN,RECLAMATION_CLOSED));
}
