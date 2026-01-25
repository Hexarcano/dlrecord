package com.hexarcano.dlrecord.auth.application.port.in;

import com.hexarcano.dlrecord.auth.application.port.in.command.LoginCommand;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface LoginUseCase {
    Maintainer logIn(LoginCommand command);
}
