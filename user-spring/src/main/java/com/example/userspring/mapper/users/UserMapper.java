package com.example.userspring.mapper.users;


import com.example.userspring.dto.users.user.UserDTO;
import com.example.userspring.dto.users.user.UserRequestDTO;
import com.example.userspring.model.users.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    User toUserEntity(UserDTO UserDTO);

    UserRequestDTO userRequestDTO(User User);

    User userEntity(UserRequestDTO UserRequestDTO);


}
