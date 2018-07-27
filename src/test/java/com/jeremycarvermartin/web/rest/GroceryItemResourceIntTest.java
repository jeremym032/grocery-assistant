package com.jeremycarvermartin.web.rest;

import com.jeremycarvermartin.GroceryAssistantApp;

import com.jeremycarvermartin.domain.GroceryItem;
import com.jeremycarvermartin.repository.GroceryItemRepository;
import com.jeremycarvermartin.service.GroceryItemService;
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
 * Test class for the GroceryItemResource REST controller.
 *
 * @see GroceryItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroceryAssistantApp.class)
public class GroceryItemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_UNIT_OF_MEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_OF_MEASUREMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    

    @Autowired
    private GroceryItemService groceryItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGroceryItemMockMvc;

    private GroceryItem groceryItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroceryItemResource groceryItemResource = new GroceryItemResource(groceryItemService);
        this.restGroceryItemMockMvc = MockMvcBuilders.standaloneSetup(groceryItemResource)
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
    public static GroceryItem createEntity(EntityManager em) {
        GroceryItem groceryItem = new GroceryItem()
            .name(DEFAULT_NAME)
            .size(DEFAULT_SIZE)
            .unitOfMeasurement(DEFAULT_UNIT_OF_MEASUREMENT)
            .description(DEFAULT_DESCRIPTION);
        return groceryItem;
    }

    @Before
    public void initTest() {
        groceryItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroceryItem() throws Exception {
        int databaseSizeBeforeCreate = groceryItemRepository.findAll().size();

        // Create the GroceryItem
        restGroceryItemMockMvc.perform(post("/api/grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groceryItem)))
            .andExpect(status().isCreated());

        // Validate the GroceryItem in the database
        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        assertThat(groceryItemList).hasSize(databaseSizeBeforeCreate + 1);
        GroceryItem testGroceryItem = groceryItemList.get(groceryItemList.size() - 1);
        assertThat(testGroceryItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroceryItem.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testGroceryItem.getUnitOfMeasurement()).isEqualTo(DEFAULT_UNIT_OF_MEASUREMENT);
        assertThat(testGroceryItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGroceryItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groceryItemRepository.findAll().size();

        // Create the GroceryItem with an existing ID
        groceryItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroceryItemMockMvc.perform(post("/api/grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groceryItem)))
            .andExpect(status().isBadRequest());

        // Validate the GroceryItem in the database
        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        assertThat(groceryItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groceryItemRepository.findAll().size();
        // set the field null
        groceryItem.setName(null);

        // Create the GroceryItem, which fails.

        restGroceryItemMockMvc.perform(post("/api/grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groceryItem)))
            .andExpect(status().isBadRequest());

        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        assertThat(groceryItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = groceryItemRepository.findAll().size();
        // set the field null
        groceryItem.setSize(null);

        // Create the GroceryItem, which fails.

        restGroceryItemMockMvc.perform(post("/api/grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groceryItem)))
            .andExpect(status().isBadRequest());

        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        assertThat(groceryItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitOfMeasurementIsRequired() throws Exception {
        int databaseSizeBeforeTest = groceryItemRepository.findAll().size();
        // set the field null
        groceryItem.setUnitOfMeasurement(null);

        // Create the GroceryItem, which fails.

        restGroceryItemMockMvc.perform(post("/api/grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groceryItem)))
            .andExpect(status().isBadRequest());

        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        assertThat(groceryItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroceryItems() throws Exception {
        // Initialize the database
        groceryItemRepository.saveAndFlush(groceryItem);

        // Get all the groceryItemList
        restGroceryItemMockMvc.perform(get("/api/grocery-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groceryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].unitOfMeasurement").value(hasItem(DEFAULT_UNIT_OF_MEASUREMENT.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getGroceryItem() throws Exception {
        // Initialize the database
        groceryItemRepository.saveAndFlush(groceryItem);

        // Get the groceryItem
        restGroceryItemMockMvc.perform(get("/api/grocery-items/{id}", groceryItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groceryItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.unitOfMeasurement").value(DEFAULT_UNIT_OF_MEASUREMENT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGroceryItem() throws Exception {
        // Get the groceryItem
        restGroceryItemMockMvc.perform(get("/api/grocery-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroceryItem() throws Exception {
        // Initialize the database
        groceryItemService.save(groceryItem);

        int databaseSizeBeforeUpdate = groceryItemRepository.findAll().size();

        // Update the groceryItem
        GroceryItem updatedGroceryItem = groceryItemRepository.findById(groceryItem.getId()).get();
        // Disconnect from session so that the updates on updatedGroceryItem are not directly saved in db
        em.detach(updatedGroceryItem);
        updatedGroceryItem
            .name(UPDATED_NAME)
            .size(UPDATED_SIZE)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .description(UPDATED_DESCRIPTION);

        restGroceryItemMockMvc.perform(put("/api/grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroceryItem)))
            .andExpect(status().isOk());

        // Validate the GroceryItem in the database
        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        assertThat(groceryItemList).hasSize(databaseSizeBeforeUpdate);
        GroceryItem testGroceryItem = groceryItemList.get(groceryItemList.size() - 1);
        assertThat(testGroceryItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroceryItem.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testGroceryItem.getUnitOfMeasurement()).isEqualTo(UPDATED_UNIT_OF_MEASUREMENT);
        assertThat(testGroceryItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGroceryItem() throws Exception {
        int databaseSizeBeforeUpdate = groceryItemRepository.findAll().size();

        // Create the GroceryItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroceryItemMockMvc.perform(put("/api/grocery-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groceryItem)))
            .andExpect(status().isBadRequest());

        // Validate the GroceryItem in the database
        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        assertThat(groceryItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroceryItem() throws Exception {
        // Initialize the database
        groceryItemService.save(groceryItem);

        int databaseSizeBeforeDelete = groceryItemRepository.findAll().size();

        // Get the groceryItem
        restGroceryItemMockMvc.perform(delete("/api/grocery-items/{id}", groceryItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroceryItem> groceryItemList = groceryItemRepository.findAll();
        assertThat(groceryItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroceryItem.class);
        GroceryItem groceryItem1 = new GroceryItem();
        groceryItem1.setId(1L);
        GroceryItem groceryItem2 = new GroceryItem();
        groceryItem2.setId(groceryItem1.getId());
        assertThat(groceryItem1).isEqualTo(groceryItem2);
        groceryItem2.setId(2L);
        assertThat(groceryItem1).isNotEqualTo(groceryItem2);
        groceryItem1.setId(null);
        assertThat(groceryItem1).isNotEqualTo(groceryItem2);
    }
}
