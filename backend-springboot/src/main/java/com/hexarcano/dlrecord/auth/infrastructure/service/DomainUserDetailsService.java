package com.hexarcano.dlrecord.auth.infrastructure.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.hexarcano.dlrecord.auth.infrastructure.entities.AuthUser;
import com.hexarcano.dlrecord.exception.UsernameNotFoundException;
import com.hexarcano.dlrecord.maintainer.infrastructure.repository.JpaMaintainerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final JpaMaintainerRepository maintainerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return maintainerRepository.findByUsername(username)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
