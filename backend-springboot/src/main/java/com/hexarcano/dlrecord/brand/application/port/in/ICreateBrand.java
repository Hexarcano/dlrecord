package com.hexarcano.dlrecord.brand.application.port.in;

import com.hexarcano.dlrecord.brand.model.entity.Brand;

/**
 * Input port (Driving Port) for the 'Create Brand' use case.
 * Defines the contract for creating a new brand within the application core.
 */
public interface ICreateBrand {
    /**
     * Executes the logic to create a new brand.
     *
     * @param brand The brand object with the data to be created.
     * @return The newly created {@link Brand}.
     */
    Brand createBrand(Brand brand);
}
