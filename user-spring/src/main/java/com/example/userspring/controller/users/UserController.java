package com.example.userspring.controller.users;

import com.example.userspring.dto.users.user.UserDTO;
import com.example.userspring.dto.users.user.UserRequestDTO;
import com.example.userspring.service.users.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.userspring.globals.URLMappings.API_PATH;
import static com.example.userspring.globals.users.UrlMappingUser.*;


@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping(USER_POST)
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserRequestDTO userRequestDTO){

        return userService.create(userRequestDTO);
    }

    @GetMapping(USER_EMAIL_GET)
    public ResponseEntity<UserDTO> findUserByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }

    @GetMapping(USER_USERNAME_GET)
    public ResponseEntity<UserDTO> findUserByUsername(@PathVariable String username){
        return userService.findByUserName(username);
    }
    @PutMapping(USER_PUT)
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return userService.update(userRequestDTO);
    }
    @DeleteMapping(USER_DELETE)
    public void delete(@PathVariable String email){

        userService.deleteUser(email);
    }
}
