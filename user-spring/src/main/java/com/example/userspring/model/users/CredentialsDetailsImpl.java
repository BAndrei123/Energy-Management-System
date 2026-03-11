package com.example.userspring.model.users;

import com.example.userspring.repository.users.UserRoleRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class CredentialsDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final UserRoleRepository userRoleRepository;

    public CredentialsDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities, UserRoleRepository userRoleRepository) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.userRoleRepository = userRoleRepository;
    }

    public static UserDetails build(Credentials credentials, UserRoleRepository userRoleRepository) {

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userRoleRepository.findByCredentialsEmail(credentials.getEmail()).get().getRole().getRole().name()));


        return new CredentialsDetailsImpl(
                credentials.getId(),
                credentials.getUsername(),
                credentials.getEmail(),
                credentials.getPassword(),
                authorities,userRoleRepository);
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
