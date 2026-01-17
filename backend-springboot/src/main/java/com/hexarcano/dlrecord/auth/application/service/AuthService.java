package com.hexarcano.dlrecord.auth.application.service;

import com.hexarcano.dlrecord.auth.application.port.in.ILogIn;
import com.hexarcano.dlrecord.auth.application.port.in.ISignUp;
import com.hexarcano.dlrecord.auth.application.port.in.dto.Credentials;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthService implements ILogIn, ISignUp {
    private final ILogIn logIn;
    private final ISignUp signUp;

    @Override
    public Maintainer signUp(Maintainer maintainer) {
        return signUp.signUp(maintainer);
    }

    @Override
    public Maintainer logIn(Credentials credentials) {
        return logIn.logIn(credentials);
    }
}
