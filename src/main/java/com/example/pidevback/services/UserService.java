package com.example.pidevback.services;

import java.util.List;

import javax.transaction.Transactional;
import com.example.pidevback.Mapper.UserMapper;
import com.example.pidevback.dto.AuthenticationRequest;
import com.example.pidevback.dto.AuthenticationResponse;
import com.example.pidevback.dto.UserDto;
import com.example.pidevback.entities.Users;
import com.example.pidevback.repositories.UserRepository;
import com.example.pidevback.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;


@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private  EmailService emailService;


    @Transactional
    public void signIn(UserDto userDto) throws Exception {

        if(userRepository.findUserByEmail(userDto.getEmail()).isPresent())
            throw new Exception("User exists !");

        Users user = UserMapper.Instance.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateTokenUsingId(String.valueOf(user.getId()));

        String link = "http://localhost:8080/auth/confirm?token=" + token;

        String body = emailService.buildEmail(user.getFullName(), link);
        emailService.sendSimpleEmail(
                user.getEmail(),
                "Please confirm your account",
                body
        );


    }

    public AuthenticationResponse logIn(AuthenticationRequest request) {
        log.info("logging");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Users user= userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User email %s not found",request.getEmail())));
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }



    public void confirmUser(String token) {

        if(jwtService.isTokenExpired(token))
            throw new IllegalStateException("token expired");

        String id = jwtService.extractUsername(token);
        Users user= userRepository.findById(Long.valueOf(id)).orElseThrow(()-> new IllegalStateException("Token invalid"));

        if(user.isEnabled())
            throw new IllegalStateException("email already confirmed");

        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public Users findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));
    }

    public List<Users> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format("User email %s not found",email)));
    }


    public void updateUser(UserDto userDto) {

        userRepository.findUserByEmail(userDto.getEmail()).orElseThrow(()-> new NotFoundException("User not found"));

        Users user = UserMapper.Instance.userDtoToUser(userDto);

        userRepository.save(user);
    }

    public void lockUser(String username) {
        Users u = userRepository.findUserByEmail(username).orElseThrow(()-> new NotFoundException("User not found"));
        u.setLoginAttempts(0);
        u.setIsLocked(true);
        userRepository.save(u);

    }


}
