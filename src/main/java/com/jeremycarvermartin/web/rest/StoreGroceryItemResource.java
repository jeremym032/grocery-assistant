package com.jeremycarvermartin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jeremycarvermartin.domain.StoreGroceryItem;
import com.jeremycarvermartin.service.StoreGroceryItemService;
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
 * REST controller for managing StoreGroceryItem.
 */
@RestController
@RequestMapping("/api")
public class StoreGroceryItemResource {

    private final Logger log = LoggerFactory.getLogger(StoreGroceryItemResource.class);

    private static final String ENTITY_NAME = "storeGroceryItem";

    private final StoreGroceryItemService storeGroceryItemService;

    public StoreGroceryItemResource(StoreGroceryItemService storeGroceryItemService) {
        this.storeGroceryItemService = storeGroceryItemService;
    }

    /**
     * POST  /store-grocery-items : Create a new storeGroceryItem.
     *
     * @param storeGroceryItem the storeGroceryItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeGroceryItem, or with status 400 (Bad Request) if the storeGroceryItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/store-grocery-items")
    @Timed
    public ResponseEntity<StoreGroceryItem> createStoreGroceryItem(@Valid @RequestBody StoreGroceryItem storeGroceryItem) throws URISyntaxException {
        log.debug("REST request to save StoreGroceryItem : {}", storeGroceryItem);
        if (storeGroceryItem.getId() != null) {
            throw new BadRequestAlertException("A new storeGroceryItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StoreGroceryItem result = storeGroceryItemService.save(storeGroceryItem);
        return ResponseEntity.created(new URI("/api/store-grocery-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /store-grocery-items : Updates an existing storeGroceryItem.
     *
     * @param storeGroceryItem the storeGroceryItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeGroceryItem,
     * or with status 400 (Bad Request) if the storeGroceryItem is not valid,
     * or with status 500 (Internal Server Error) if the storeGroceryItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/store-grocery-items")
    @Timed
    public ResponseEntity<StoreGroceryItem> updateStoreGroceryItem(@Valid @RequestBody StoreGroceryItem storeGroceryItem) throws URISyntaxException {
        log.debug("REST request to update StoreGroceryItem : {}", storeGroceryItem);
        if (storeGroceryItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StoreGroceryItem result = storeGroceryItemService.save(storeGroceryItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, storeGroceryItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /store-grocery-items : get all the storeGroceryItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of storeGroceryItems in body
     */
    @GetMapping("/store-grocery-items")
    @Timed
    public List<StoreGroceryItem> getAllStoreGroceryItems() {
        log.debug("REST request to get all StoreGroceryItems");
        return storeGroceryItemService.findAll();
    }

    /**
     * GET  /store-grocery-items/:id : get the "id" storeGroceryItem.
     *
     * @param id the id of the storeGroceryItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeGroceryItem, or with status 404 (Not Found)
     */
    @GetMapping("/store-grocery-items/{id}")
    @Timed
    public ResponseEntity<StoreGroceryItem> getStoreGroceryItem(@PathVariable Long id) {
        log.debug("REST request to get StoreGroceryItem : {}", id);
        Optional<StoreGroceryItem> storeGroceryItem = storeGroceryItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storeGroceryItem);
    }

    /**
     * DELETE  /store-grocery-items/:id : delete the "id" storeGroceryItem.
     *
     * @param id the id of the storeGroceryItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/store-grocery-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteStoreGroceryItem(@PathVariable Long id) {
        log.debug("REST request to delete StoreGroceryItem : {}", id);
        storeGroceryItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
