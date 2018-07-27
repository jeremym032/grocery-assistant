package com.jeremycarvermartin.service;

import com.jeremycarvermartin.domain.GroceryItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing GroceryItem.
 */
public interface GroceryItemService {

    /**
     * Save a groceryItem.
     *
     * @param groceryItem the entity to save
     * @return the persisted entity
     */
    GroceryItem save(GroceryItem groceryItem);

    /**
     * Get all the groceryItems.
     *
     * @return the list of entities
     */
    List<GroceryItem> findAll();


    /**
     * Get the "id" groceryItem.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<GroceryItem> findOne(Long id);

    /**
     * Delete the "id" groceryItem.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
