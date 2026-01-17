package com.hexarcano.dlrecord.auth.application.implementation;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexarcano.dlrecord.auth.application.port.in.ISignUp;
import com.hexarcano.dlrecord.auth.application.port.out.IAuthRepository;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignUp implements ISignUp {
    private final IAuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Maintainer signUp(Maintainer maintainer) {
        Maintainer newMaintainer = new Maintainer(
                maintainer.getUuid(),
                maintainer.getUsername(),
                maintainer.getEmail(),
                passwordEncoder.encode(maintainer.getPassword()),
                maintainer.getIsAdmin());

        return authRepository.signUp(newMaintainer);
    }
}
