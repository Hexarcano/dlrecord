package com.hexarcano.dlrecord.auth.infrastructure.repository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.hexarcano.dlrecord.auth.application.port.in.dto.Credentials;
import com.hexarcano.dlrecord.auth.application.port.out.IAuthRepository;
import com.hexarcano.dlrecord.auth.infrastructure.entities.AuthUser;
import com.hexarcano.dlrecord.maintainer.infrastructure.entities.MaintainerEntity;
import com.hexarcano.dlrecord.maintainer.infrastructure.repository.JpaMaintainerRepository;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthRepositoryAdapter implements IAuthRepository {
    private final JpaMaintainerRepository maintainerRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public Maintainer signUp(Maintainer maintainerModel) {
        MaintainerEntity entity = MaintainerEntity.fromDomainModel(maintainerModel);

        return maintainerRepository.save(entity).toDomainModel();
    }

    @Override
    public Maintainer logIn(Credentials credentials) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.username(),
                        credentials.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return authUser.getEntity().toDomainModel();
    }
}
