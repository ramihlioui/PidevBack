package com.example.pidevback.Mapper;

import com.example.pidevback.dto.UserDto;
import com.example.pidevback.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper Instance = Mappers.getMapper(UserMapper.class);


    Users userDtoToUser(UserDto userDto);

    UserDto usersToUsersDto(Users user);


}
