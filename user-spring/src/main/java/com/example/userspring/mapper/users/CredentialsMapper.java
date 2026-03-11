package com.example.userspring.mapper.users;


import com.example.userspring.dto.users.credentials.CredentialsDTO;
import com.example.userspring.dto.users.credentials.CredentialsRequestDTO;
import com.example.userspring.model.users.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsDTO toCredentialsDTO(Credentials credentials);

    Credentials toCredentialsEntity(CredentialsDTO credentialsDTO);

    CredentialsRequestDTO credentialsRequestDTO(Credentials credentials);

    Credentials credentialsEntity(CredentialsRequestDTO credentialsRequestDTO);
}
