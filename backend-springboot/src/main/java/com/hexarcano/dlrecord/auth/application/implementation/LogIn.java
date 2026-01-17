package com.hexarcano.dlrecord.auth.application.implementation;

import com.hexarcano.dlrecord.auth.application.port.in.ILogIn;
import com.hexarcano.dlrecord.auth.application.port.in.dto.Credentials;
import com.hexarcano.dlrecord.auth.application.port.out.IAuthRepository;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogIn implements ILogIn {
    private final IAuthRepository authRepository;

    @Override
    public Maintainer logIn(Credentials credentials) {
        return authRepository.logIn(credentials);
    }
}