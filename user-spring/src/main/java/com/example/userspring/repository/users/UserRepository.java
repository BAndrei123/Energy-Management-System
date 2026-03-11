package com.example.userspring.repository.users;


import com.example.userspring.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCredentialsId(Long id);

    void deleteUserByCredentialsId(Long id);
}
