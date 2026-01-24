package com.hexarcano.dlrecord.maintainer.application.port.in;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface RetrieveMaintainerUseCase {
    Optional<Maintainer> findById(String uuid);

    List<Maintainer> findAll();
}
