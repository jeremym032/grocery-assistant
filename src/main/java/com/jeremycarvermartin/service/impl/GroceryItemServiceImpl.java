package com.jeremycarvermartin.service.impl;

import com.jeremycarvermartin.service.GroceryItemService;
import com.jeremycarvermartin.domain.GroceryItem;
import com.jeremycarvermartin.repository.GroceryItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing GroceryItem.
 */
@Service
@Transactional
public class GroceryItemServiceImpl implements GroceryItemService {

    private final Logger log = LoggerFactory.getLogger(GroceryItemServiceImpl.class);

    private final GroceryItemRepository groceryItemRepository;

    public GroceryItemServiceImpl(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    /**
     * Save a groceryItem.
     *
     * @param groceryItem the entity to save
     * @return the persisted entity
     */
    @Override
    public GroceryItem save(GroceryItem groceryItem) {
        log.debug("Request to save GroceryItem : {}", groceryItem);        return groceryItemRepository.save(groceryItem);
    }

    /**
     * Get all the groceryItems.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GroceryItem> findAll() {
        log.debug("Request to get all GroceryItems");
        return groceryItemRepository.findAll();
    }


    /**
     * Get one groceryItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GroceryItem> findOne(Long id) {
        log.debug("Request to get GroceryItem : {}", id);
        return groceryItemRepository.findById(id);
    }

    /**
     * Delete the groceryItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroceryItem : {}", id);
        groceryItemRepository.deleteById(id);
    }
}
