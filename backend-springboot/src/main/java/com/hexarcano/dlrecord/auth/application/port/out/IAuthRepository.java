package com.hexarcano.dlrecord.auth.application.port.out;

import com.hexarcano.dlrecord.auth.application.port.in.dto.Credentials;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface IAuthRepository {
    Maintainer signUp(Maintainer maintainer);

    Maintainer logIn(Credentials credentials);
}
