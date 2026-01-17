package com.hexarcano.dlrecord.auth.application.port.in;

import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface ISignUp {
    Maintainer signUp(Maintainer maintainer);
}
