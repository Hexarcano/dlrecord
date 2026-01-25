package com.hexarcano.dlrecord.auth.application.implementation;

import com.hexarcano.dlrecord.auth.application.port.in.LoginUseCase;
import com.hexarcano.dlrecord.auth.application.port.in.command.LoginCommand;
import com.hexarcano.dlrecord.auth.application.port.out.AuthRepositoryPort;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogIn implements LoginUseCase {
    private final AuthRepositoryPort authRepository;

    @Override
    public Maintainer logIn(LoginCommand command) {
        return authRepository.logIn(command.username(), command.password());
    }
}