package com.hexarcano.dlrecord.maintainer.application.port.out;

import java.util.List;
import java.util.Optional;

import com.hexarcano.dlrecord.maintainer.model.Maintainer;

public interface IMaintainerRepository {
    Maintainer save(Maintainer maintainer);

    Optional<Maintainer> update(String uuid, Maintainer maintainer);

    Optional<Maintainer> findByUsername(String username);

    Optional<Maintainer> findById(String uuid);

    List<Maintainer> findAll();

    boolean delete(String uuid);
}
