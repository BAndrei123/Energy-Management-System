package com.example.userspring.mapper.users;


import com.example.userspring.dto.users.role.UserRoleDTO;
import com.example.userspring.dto.users.role.UserRoleRequestDTO;
import com.example.userspring.model.users.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRoleDTO toUserRoleDTO(UserRole userRole);

    UserRole toUserRoleEntity(UserRoleDTO userRoleDTO);

    UserRoleRequestDTO UserRoleRequestDTO(UserRole userRole);

    UserRole UserRoleEntity(UserRoleRequestDTO userRoleRequestDTO);


}
