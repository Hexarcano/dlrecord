package com.hexarcano.dlrecord.auth.application.port.out;

import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;

public interface AuthRepositoryPort {
    Maintainer signUp(Maintainer maintainer);

    Maintainer logIn(String username, String password);
}
