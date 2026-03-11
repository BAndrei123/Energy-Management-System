package com.example.userspring.mapper.users;

import com.example.userspring.dto.users.role.RoleDTO;
import com.example.userspring.dto.users.role.RoleRequestDTO;
import com.example.userspring.model.users.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toRoleDTO(Role role);

    Role toRoleEntity(RoleDTO roleDTO);

    RoleRequestDTO roleRequestDTO(Role role);

    Role roleEntity(RoleRequestDTO RoleRequestDTO);
}
