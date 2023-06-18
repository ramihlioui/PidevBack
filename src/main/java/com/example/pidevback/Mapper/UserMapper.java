package com.example.pidevback.Mapper;

import com.example.pidevback.dto.RoleDto;
import com.example.pidevback.dto.UserDto;
import com.example.pidevback.entities.Role;
import com.example.pidevback.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper Instance = Mappers.getMapper(UserMapper.class);


    Users userDtoToUser(UserDto userDto);

    UserDto usersToUsersDto(Users user);

    Role roleDtoToRole(RoleDto roleDto);

    List<RoleDto> rolesToRoleDTOs(List<Role> roles);


}
