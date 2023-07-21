package com.exemple.pidevback.service;


import com.example.pidevback.entities.Users;
import com.example.pidevback.repositories.UserRepository;
import com.example.pidevback.security.JwtService;
import com.example.pidevback.services.UserService;
import com.exemple.pidevback.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Spy
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;



    @Test
    public void confirmUserTestValid(){
    log.info("Test Start");
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYW1paGxpb3VpMkBnbWFpbC5jb20iLCJpYXQiOjE2ODk5MjcwMTksImV4cCI6MTY4OTkyNzYxOX0.PbP7VWECRzeVIS4OsRkaAssekTmwEyV72BidhA6akA40";
        Mockito.doReturn(false).when(jwtService).isTokenExpired(Mockito.anyString());

        Mockito.doReturn("ramihlioui.tsuna@gmail.com").when(jwtService).extractUsername(Mockito.anyString());
        Optional<Users> user = Optional.of(Constants.createUser());
        Mockito.doReturn(user).when(userRepository).findUserByEmail(Mockito.anyString());

        userService.confirmUser(token);
        log.info("Test Success");

    }

    @Test
    public void confirmUserTestTokenExpired(){
        log.info("Test Start");
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYW1paGxpb3VpMkBnbWFpbC5jb20iLCJpYXQiOjE2ODk5MjcwMTksImV4cCI6MTY4OTkyNzYxOX0.PbP7VWECRzeVIS4OsRkaAssekTmwEyV72BidhA6akA40";
        Mockito.doReturn(false).when(jwtService).isTokenExpired(Mockito.anyString());

        Throwable exceptionThrowable = assertThrows(IllegalStateException.class,
                () -> userService.confirmUser(token));
        assertEquals("Token invalid", exceptionThrowable.getMessage());


        log.info("Test Success");

    }

    @Test
    public void confirmUserTestUserEnabled(){
        log.info("Test Start");
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYW1paGxpb3VpMkBnbWFpbC5jb20iLCJpYXQiOjE2ODk5MjcwMTksImV4cCI6MTY4OTkyNzYxOX0.PbP7VWECRzeVIS4OsRkaAssekTmwEyV72BidhA6akA40";
        Mockito.doReturn(false).when(jwtService).isTokenExpired(Mockito.anyString());

        Mockito.doReturn("ramihlioui.tsuna@gmail.com").when(jwtService).extractUsername(Mockito.anyString());
        Optional<Users> user = Optional.of(Constants.createUser());
        user.get().setIsEnabled(true);
        Mockito.doReturn(user).when(userRepository).findUserByEmail(Mockito.anyString());


        Throwable exceptionThrowable = assertThrows(IllegalStateException.class,
                () -> userService.confirmUser(token));
        assertEquals("email already confirmed", exceptionThrowable.getMessage());


        log.info("Test Success");

    }

}
