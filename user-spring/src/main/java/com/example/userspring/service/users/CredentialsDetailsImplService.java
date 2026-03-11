package com.example.userspring.service.users;

import com.example.userspring.model.users.Credentials;
import com.example.userspring.model.users.CredentialsDetailsImpl;
import com.example.userspring.repository.users.CredentialsRepository;
import com.example.userspring.repository.users.UserRoleRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialsDetailsImplService implements UserDetailsService {
    private final CredentialsRepository credentialsRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials credentials = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return CredentialsDetailsImpl.build(credentials,userRoleRepository);
    }

}
