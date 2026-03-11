package com.example.userspring.controller.users;

import com.example.userspring.dto.users.role.RoleDTO;
import com.example.userspring.dto.users.role.RoleRequestDTO;
import com.example.userspring.service.users.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.userspring.globals.URLMappings.API_PATH;
import static com.example.userspring.globals.users.UrlMappingRole.*;


@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping(ROLE_POST)
    public ResponseEntity<RoleDTO> create(@RequestBody @Valid RoleRequestDTO roleRequestDTO){
        return roleService.create(roleRequestDTO);
    }

    @GetMapping(ROLE_GET)
    public ResponseEntity<RoleDTO> findById(@PathVariable Long id){
        return roleService.findById(id);
    }

    @PutMapping(ROLE_PUT)
    public ResponseEntity<RoleDTO> update(@RequestBody @Valid RoleRequestDTO roleRequestDTO, @PathVariable Long id){
        return roleService.updateRole(roleRequestDTO,id);
    }

    @DeleteMapping(ROLE_DELETE)
    public void delete(@PathVariable Long id){
        roleService.delete(id);
    }

}
