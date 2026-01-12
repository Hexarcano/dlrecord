package com.hexarcano.dlrecord.brand.application.port.in;

/**
 * Input port (Driving Port) for the 'Delete Brand' use case.
 * Defines the contract for deleting a brand from the application core.
 */
public interface IDeleteBrand {
    /**
     * Executes the logic to delete a brand by its unique identifier.
     *
     * @param uuid The unique identifier of the brand to be deleted.
     * @return {@code true} if the deletion was successful, {@code false} otherwise
     *         (e.g., if the brand was not found).
     */
    boolean deleteBrand(String uuid);
}
