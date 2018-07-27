package com.jeremycarvermartin.service.impl;

import com.jeremycarvermartin.service.StoreGroceryItemService;
import com.jeremycarvermartin.domain.StoreGroceryItem;
import com.jeremycarvermartin.repository.StoreGroceryItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing StoreGroceryItem.
 */
@Service
@Transactional
public class StoreGroceryItemServiceImpl implements StoreGroceryItemService {

    private final Logger log = LoggerFactory.getLogger(StoreGroceryItemServiceImpl.class);

    private final StoreGroceryItemRepository storeGroceryItemRepository;

    public StoreGroceryItemServiceImpl(StoreGroceryItemRepository storeGroceryItemRepository) {
        this.storeGroceryItemRepository = storeGroceryItemRepository;
    }

    /**
     * Save a storeGroceryItem.
     *
     * @param storeGroceryItem the entity to save
     * @return the persisted entity
     */
    @Override
    public StoreGroceryItem save(StoreGroceryItem storeGroceryItem) {
        log.debug("Request to save StoreGroceryItem : {}", storeGroceryItem);        return storeGroceryItemRepository.save(storeGroceryItem);
    }

    /**
     * Get all the storeGroceryItems.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<StoreGroceryItem> findAll() {
        log.debug("Request to get all StoreGroceryItems");
        return storeGroceryItemRepository.findAll();
    }


    /**
     * Get one storeGroceryItem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StoreGroceryItem> findOne(Long id) {
        log.debug("Request to get StoreGroceryItem : {}", id);
        return storeGroceryItemRepository.findById(id);
    }

    /**
     * Delete the storeGroceryItem by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StoreGroceryItem : {}", id);
        storeGroceryItemRepository.deleteById(id);
    }
}
