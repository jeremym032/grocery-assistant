package com.jeremycarvermartin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jeremycarvermartin.domain.Store;
import com.jeremycarvermartin.service.StoreService;
import com.jeremycarvermartin.web.rest.errors.BadRequestAlertException;
import com.jeremycarvermartin.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);

    private static final String ENTITY_NAME = "store";

    private final StoreService storeService;

    public StoreResource(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * POST  /stores : Create a new store.
     *
     * @param store the store to create
     * @return the ResponseEntity with status 201 (Created) and with body the new store, or with status 400 (Bad Request) if the store has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stores")
    @Timed
    public ResponseEntity<Store> createStore(@Valid @RequestBody Store store) throws URISyntaxException {
        log.debug("REST request to save Store : {}", store);
        if (store.getId() != null) {
            throw new BadRequestAlertException("A new store cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Store result = storeService.save(store);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param store the store to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated store,
     * or with status 400 (Bad Request) if the store is not valid,
     * or with status 500 (Internal Server Error) if the store couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stores")
    @Timed
    public ResponseEntity<Store> updateStore(@Valid @RequestBody Store store) throws URISyntaxException {
        log.debug("REST request to update Store : {}", store);
        if (store.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Store result = storeService.save(store);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, store.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     */
    @GetMapping("/stores")
    @Timed
    public List<Store> getAllStores() {
        log.debug("REST request to get all Stores");
        return storeService.findAll();
    }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the store to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the store, or with status 404 (Not Found)
     */
    @GetMapping("/stores/{id}")
    @Timed
    public ResponseEntity<Store> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        Optional<Store> store = storeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(store);
    }

    /**
     * DELETE  /stores/:id : delete the "id" store.
     *
     * @param id the id of the store to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stores/{id}")
    @Timed
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
