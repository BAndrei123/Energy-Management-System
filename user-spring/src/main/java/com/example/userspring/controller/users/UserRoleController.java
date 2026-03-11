package com.example.userspring.controller.users;

import com.example.userspring.dto.users.role.UserRoleDTO;
import com.example.userspring.dto.users.role.UserRoleRequestDTO;
import com.example.userspring.service.users.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.userspring.globals.URLMappings.API_PATH;
import static com.example.userspring.globals.users.UrlMappingUserRole.*;


@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;

    @PostMapping(USER_ROLE_POST)
    public ResponseEntity<UserRoleDTO> create(@RequestBody @Valid UserRoleRequestDTO userRoleRequestDTO){
        return userRoleService.create(userRoleRequestDTO);
    }

    @GetMapping(USER_ROLE_EMAIL_GET)
    public ResponseEntity<UserRoleDTO> findUserRole(@PathVariable String email){
        return userRoleService.findByCredentials(email);
    }
    @GetMapping(USER_ROLE_USERNAME_GET)
    public ResponseEntity<UserRoleDTO> findUserRoleByUsername(@PathVariable String username){
        return userRoleService.findByUsername(username);
    }
    @PutMapping(USER_ROLE_PUT)
    public ResponseEntity<UserRoleDTO> update(@RequestBody UserRoleRequestDTO userRoleRequestDTO){
        return userRoleService.update(userRoleRequestDTO);
    }

    @DeleteMapping(USER_ROLE_DELETE)
    public void delete(@PathVariable String email){
        userRoleService.delete(email);
    }

    @GetMapping(USER_GET_ALL_BY_ROLE)
    public ResponseEntity<List<UserRoleDTO>> findAll(@PathVariable String role){
        return userRoleService.findAllByRole(role);
    }
}
