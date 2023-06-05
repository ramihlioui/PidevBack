package com.example.pidevback.services;

import com.example.pidevback.Mapper.UserMapper;
import com.example.pidevback.dto.UserDto;
import com.example.pidevback.entities.Users;
import com.example.pidevback.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users saveUser(UserDto userDto){
        Users user = UserMapper.Instance.userDtoToUser(userDto);
        return userRepository.save(user);
    }


}
