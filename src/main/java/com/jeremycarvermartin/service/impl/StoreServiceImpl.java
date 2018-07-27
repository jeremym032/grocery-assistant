package com.jeremycarvermartin.service.impl;

import com.jeremycarvermartin.service.StoreService;
import com.jeremycarvermartin.domain.Store;
import com.jeremycarvermartin.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Store.
 */
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    /**
     * Save a store.
     *
     * @param store the entity to save
     * @return the persisted entity
     */
    @Override
    public Store save(Store store) {
        log.debug("Request to save Store : {}", store);        return storeRepository.save(store);
    }

    /**
     * Get all the stores.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Store> findAll() {
        log.debug("Request to get all Stores");
        return storeRepository.findAll();
    }


    /**
     * Get one store by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Store> findOne(Long id) {
        log.debug("Request to get Store : {}", id);
        return storeRepository.findById(id);
    }

    /**
     * Delete the store by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.deleteById(id);
    }
}
