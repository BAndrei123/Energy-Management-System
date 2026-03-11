package com.example.userspring.repository.users;


import com.example.userspring.model.users.ERole;
import com.example.userspring.model.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRole(ERole role);
}
