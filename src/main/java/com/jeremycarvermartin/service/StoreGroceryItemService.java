package com.jeremycarvermartin.service;

import com.jeremycarvermartin.domain.StoreGroceryItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing StoreGroceryItem.
 */
public interface StoreGroceryItemService {

    /**
     * Save a storeGroceryItem.
     *
     * @param storeGroceryItem the entity to save
     * @return the persisted entity
     */
    StoreGroceryItem save(StoreGroceryItem storeGroceryItem);

    /**
     * Get all the storeGroceryItems.
     *
     * @return the list of entities
     */
    List<StoreGroceryItem> findAll();


    /**
     * Get the "id" storeGroceryItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StoreGroceryItem> findOne(Long id);

    /**
     * Delete the "id" storeGroceryItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
