package com.example.userspring.repository.users;


import com.example.userspring.model.users.Role;
import com.example.userspring.model.users.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByCredentialsId(Long id);

    Optional<UserRole> findByCredentialsEmail(String email);

    void deleteByCredentialsId(Long id);

    List<UserRole> findAllByRole(Role role);
}
