package com.hexarcano.dlrecord.auth.application.implementation;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexarcano.dlrecord.auth.application.port.in.SignUpUseCase;
import com.hexarcano.dlrecord.auth.application.port.in.command.SignUpCommand;
import com.hexarcano.dlrecord.auth.application.port.out.AuthRepositoryPort;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignUp implements SignUpUseCase {
    private final AuthRepositoryPort authRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Maintainer signUp(SignUpCommand command) {
        Maintainer newMaintainer = new Maintainer(
                null,
                command.username(),
                command.email(),
                passwordEncoder.encode(command.password()),
                command.isAdmin());

        return authRepository.signUp(newMaintainer);
    }
}
