package com.infy.bppondc.service;

import com.infy.bppondc.service.dto.CartDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.bppondc.domain.Cart}.
 */
public interface CartService {
    /**
     * Save a cart.
     *
     * @param cartDTO the entity to save.
     * @return the persisted entity.
     */
    CartDTO save(CartDTO cartDTO);

    /**
     * Updates a cart.
     *
     * @param cartDTO the entity to update.
     * @return the persisted entity.
     */
    CartDTO update(CartDTO cartDTO);

    /**
     * Partially updates a cart.
     *
     * @param cartDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CartDTO> partialUpdate(CartDTO cartDTO);

    /**
     * Get all the carts.
     *
     * @return the list of entities.
     */
    List<CartDTO> findAll();

    /**
     * Get the "id" cart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CartDTO> findOne(Long id);

    /**
     * Delete the "id" cart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void deleteByReferenceId(String referenceId);

    void deleteByReferenceIdAndProductName(String referenceId, String productName);

    void deleteByProductName(String productName);
}
