package com.hexarcano.dlrecord.auth.application.port.in;

import com.hexarcano.dlrecord.auth.application.port.in.dto.Credentials;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface ILogIn {
    Maintainer logIn(Credentials credentials);
}
