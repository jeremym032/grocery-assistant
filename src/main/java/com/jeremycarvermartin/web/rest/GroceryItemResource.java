package com.jeremycarvermartin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jeremycarvermartin.domain.GroceryItem;
import com.jeremycarvermartin.service.GroceryItemService;
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
 * REST controller for managing GroceryItem.
 */
@RestController
@RequestMapping("/api")
public class GroceryItemResource {

    private final Logger log = LoggerFactory.getLogger(GroceryItemResource.class);

    private static final String ENTITY_NAME = "groceryItem";

    private final GroceryItemService groceryItemService;

    public GroceryItemResource(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    /**
     * POST  /grocery-items : Create a new groceryItem.
     *
     * @param groceryItem the groceryItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groceryItem, or with status 400 (Bad Request) if the groceryItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grocery-items")
    @Timed
    public ResponseEntity<GroceryItem> createGroceryItem(@Valid @RequestBody GroceryItem groceryItem) throws URISyntaxException {
        log.debug("REST request to save GroceryItem : {}", groceryItem);
        if (groceryItem.getId() != null) {
            throw new BadRequestAlertException("A new groceryItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroceryItem result = groceryItemService.save(groceryItem);
        return ResponseEntity.created(new URI("/api/grocery-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grocery-items : Updates an existing groceryItem.
     *
     * @param groceryItem the groceryItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groceryItem,
     * or with status 400 (Bad Request) if the groceryItem is not valid,
     * or with status 500 (Internal Server Error) if the groceryItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grocery-items")
    @Timed
    public ResponseEntity<GroceryItem> updateGroceryItem(@Valid @RequestBody GroceryItem groceryItem) throws URISyntaxException {
        log.debug("REST request to update GroceryItem : {}", groceryItem);
        if (groceryItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroceryItem result = groceryItemService.save(groceryItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groceryItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grocery-items : get all the groceryItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groceryItems in body
     */
    @GetMapping("/grocery-items")
    @Timed
    public List<GroceryItem> getAllGroceryItems() {
        log.debug("REST request to get all GroceryItems");
        return groceryItemService.findAll();
    }

    /**
     * GET  /grocery-items/:id : get the "id" groceryItem.
     *
     * @param id the id of the groceryItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groceryItem, or with status 404 (Not Found)
     */
    @GetMapping("/grocery-items/{id}")
    @Timed
    public ResponseEntity<GroceryItem> getGroceryItem(@PathVariable Long id) {
        log.debug("REST request to get GroceryItem : {}", id);
        Optional<GroceryItem> groceryItem = groceryItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groceryItem);
    }

    /**
     * DELETE  /grocery-items/:id : delete the "id" groceryItem.
     *
     * @param id the id of the groceryItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grocery-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable Long id) {
        log.debug("REST request to delete GroceryItem : {}", id);
        groceryItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
