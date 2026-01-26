package com.hexarcano.dlrecord.auth.application.service;

import com.hexarcano.dlrecord.auth.application.port.in.LoginUseCase;
import com.hexarcano.dlrecord.auth.application.port.in.SignUpUseCase;
import com.hexarcano.dlrecord.auth.application.port.in.command.LoginCommand;
import com.hexarcano.dlrecord.auth.application.port.in.command.SignUpCommand;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthService implements LoginUseCase, SignUpUseCase {
    private final LoginUseCase loginUseCase;
    private final SignUpUseCase signUpUseCase;

    @Override
    public Maintainer signUp(SignUpCommand command) {
        return signUpUseCase.signUp(command);
    }

    @Override
    public Maintainer logIn(LoginCommand command) {
        return loginUseCase.logIn(command);
    }
}
