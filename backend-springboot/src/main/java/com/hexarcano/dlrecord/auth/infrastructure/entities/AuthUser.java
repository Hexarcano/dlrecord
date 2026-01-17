package com.hexarcano.dlrecord.auth.infrastructure.entities;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexarcano.dlrecord.maintainer.infrastructure.entities.MaintainerEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthUser implements UserDetails {
    private final MaintainerEntity entity;

    @Override
    public String getUsername() {
        return entity.getUsername();
    }

    @Override
    public @Nullable String getPassword() {
        return entity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return java.util.Collections.emptyList();
    }
}
