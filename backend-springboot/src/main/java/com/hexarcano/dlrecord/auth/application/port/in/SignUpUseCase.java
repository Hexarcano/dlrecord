package com.hexarcano.dlrecord.auth.application.port.in;

import com.hexarcano.dlrecord.auth.application.port.in.command.SignUpCommand;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface SignUpUseCase {
    Maintainer signUp(SignUpCommand command);
}
