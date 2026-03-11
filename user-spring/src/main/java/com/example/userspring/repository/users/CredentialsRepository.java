package com.example.userspring.repository.users;


import com.example.userspring.model.users.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials,Long> {

    Optional<Credentials> findByEmailAndPassword(String email, String password);

    Optional<Credentials>  findByEmail(String email);

    Optional<Credentials> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
