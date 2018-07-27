package com.jeremycarvermartin.web.rest;

import com.jeremycarvermartin.GroceryAssistantApp;

import com.jeremycarvermartin.domain.StoreGroceryItem;
import com.jeremycarvermartin.domain.Store;
import com.jeremycarvermartin.domain.Item;
import com.jeremycarvermartin.repository.StoreGroceryItemRepository;
import com.jeremycarvermartin.service.StoreGroceryItemService;
import com.jeremycarvermartin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.jeremycarvermartin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StoreGroceryItemResource REST controller.
 *
 * @see StoreGroceryItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroceryAssistantApp.class)
public class StoreGroceryItemResourceIntTest {

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Boolean DEFAULT_FAVORITE_IND = false;
    private static final Boolean UPDATED_FAVORITE_IND = true;

    @Autowired
    private StoreGroceryItemRepository storeGroceryItemRepository;

    

    @Autowired
    private StoreGroceryItemService storeGroceryItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStoreGroceryItemMockMvc;

    private StoreGroceryItem storeGroceryItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StoreGroceryItemResource storeGroceryItemResource = new StoreGroceryItemResource(storeGroceryItemService);
        this.restStoreGroceryItemMockMvc = MockMvcBuilders.standaloneSetup(storeGroceryItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StoreGroceryItem createEntity(EntityManager em) {
        StoreGroceryItem storeGroceryItem = new StoreGroceryItem()
            .price(DEFAULT_PRICE)
            .favoriteInd(DEFAULT_FAVORITE_IND);
        // Add required entity
        Store store = StoreResourceIntTest.createEntity(em);
        em.persist(store);
        em.flush();
        storeGroceryItem.setStore(store);
        // Add required entity
        Item item = ItemResourceIntTest.createEntity(em);
        em.persist(item);
        em.flush();
        storeGroceryItem.setItem(item);
        return storeGroceryItem;
    }

    @Before
    public void initTest() {
        storeGroceryItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createStoreGroceryItem() throws Exception {
        int databaseSizeBeforeCreate = storeGroceryItemRepository.findAll().size();

        // Create the StoreGroceryItem
        restStoreGroceryItemMockMvc.perform(post("/api/store-grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeGroceryItem)))
            .andExpect(status().isCreated());

        // Validate the StoreGroceryItem in the database
        List<StoreGroceryItem> storeGroceryItemList = storeGroceryItemRepository.findAll();
        assertThat(storeGroceryItemList).hasSize(databaseSizeBeforeCreate + 1);
        StoreGroceryItem testStoreGroceryItem = storeGroceryItemList.get(storeGroceryItemList.size() - 1);
        assertThat(testStoreGroceryItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testStoreGroceryItem.isFavoriteInd()).isEqualTo(DEFAULT_FAVORITE_IND);
    }

    @Test
    @Transactional
    public void createStoreGroceryItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeGroceryItemRepository.findAll().size();

        // Create the StoreGroceryItem with an existing ID
        storeGroceryItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreGroceryItemMockMvc.perform(post("/api/store-grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeGroceryItem)))
            .andExpect(status().isBadRequest());

        // Validate the StoreGroceryItem in the database
        List<StoreGroceryItem> storeGroceryItemList = storeGroceryItemRepository.findAll();
        assertThat(storeGroceryItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeGroceryItemRepository.findAll().size();
        // set the field null
        storeGroceryItem.setPrice(null);

        // Create the StoreGroceryItem, which fails.

        restStoreGroceryItemMockMvc.perform(post("/api/store-grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeGroceryItem)))
            .andExpect(status().isBadRequest());

        List<StoreGroceryItem> storeGroceryItemList = storeGroceryItemRepository.findAll();
        assertThat(storeGroceryItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStoreGroceryItems() throws Exception {
        // Initialize the database
        storeGroceryItemRepository.saveAndFlush(storeGroceryItem);

        // Get all the storeGroceryItemList
        restStoreGroceryItemMockMvc.perform(get("/api/store-grocery-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storeGroceryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].favoriteInd").value(hasItem(DEFAULT_FAVORITE_IND.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getStoreGroceryItem() throws Exception {
        // Initialize the database
        storeGroceryItemRepository.saveAndFlush(storeGroceryItem);

        // Get the storeGroceryItem
        restStoreGroceryItemMockMvc.perform(get("/api/store-grocery-items/{id}", storeGroceryItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(storeGroceryItem.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.favoriteInd").value(DEFAULT_FAVORITE_IND.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStoreGroceryItem() throws Exception {
        // Get the storeGroceryItem
        restStoreGroceryItemMockMvc.perform(get("/api/store-grocery-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStoreGroceryItem() throws Exception {
        // Initialize the database
        storeGroceryItemService.save(storeGroceryItem);

        int databaseSizeBeforeUpdate = storeGroceryItemRepository.findAll().size();

        // Update the storeGroceryItem
        StoreGroceryItem updatedStoreGroceryItem = storeGroceryItemRepository.findById(storeGroceryItem.getId()).get();
        // Disconnect from session so that the updates on updatedStoreGroceryItem are not directly saved in db
        em.detach(updatedStoreGroceryItem);
        updatedStoreGroceryItem
            .price(UPDATED_PRICE)
            .favoriteInd(UPDATED_FAVORITE_IND);

        restStoreGroceryItemMockMvc.perform(put("/api/store-grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStoreGroceryItem)))
            .andExpect(status().isOk());

        // Validate the StoreGroceryItem in the database
        List<StoreGroceryItem> storeGroceryItemList = storeGroceryItemRepository.findAll();
        assertThat(storeGroceryItemList).hasSize(databaseSizeBeforeUpdate);
        StoreGroceryItem testStoreGroceryItem = storeGroceryItemList.get(storeGroceryItemList.size() - 1);
        assertThat(testStoreGroceryItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testStoreGroceryItem.isFavoriteInd()).isEqualTo(UPDATED_FAVORITE_IND);
    }

    @Test
    @Transactional
    public void updateNonExistingStoreGroceryItem() throws Exception {
        int databaseSizeBeforeUpdate = storeGroceryItemRepository.findAll().size();

        // Create the StoreGroceryItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStoreGroceryItemMockMvc.perform(put("/api/store-grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeGroceryItem)))
            .andExpect(status().isBadRequest());

        // Validate the StoreGroceryItem in the database
        List<StoreGroceryItem> storeGroceryItemList = storeGroceryItemRepository.findAll();
        assertThat(storeGroceryItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStoreGroceryItem() throws Exception {
        // Initialize the database
        storeGroceryItemService.save(storeGroceryItem);

        int databaseSizeBeforeDelete = storeGroceryItemRepository.findAll().size();

        // Get the storeGroceryItem
        restStoreGroceryItemMockMvc.perform(delete("/api/store-grocery-items/{id}", storeGroceryItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StoreGroceryItem> storeGroceryItemList = storeGroceryItemRepository.findAll();
        assertThat(storeGroceryItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreGroceryItem.class);
        StoreGroceryItem storeGroceryItem1 = new StoreGroceryItem();
        storeGroceryItem1.setId(1L);
        StoreGroceryItem storeGroceryItem2 = new StoreGroceryItem();
        storeGroceryItem2.setId(storeGroceryItem1.getId());
        assertThat(storeGroceryItem1).isEqualTo(storeGroceryItem2);
        storeGroceryItem2.setId(2L);
        assertThat(storeGroceryItem1).isNotEqualTo(storeGroceryItem2);
        storeGroceryItem1.setId(null);
        assertThat(storeGroceryItem1).isNotEqualTo(storeGroceryItem2);
    }
}
