package com.books.server.web.rest;

import com.books.server.BooksApp;
import com.books.server.domain.Reading;
import com.books.server.repository.ReadingRepository;
import com.books.server.service.ReadingService;
import com.books.server.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.books.server.web.rest.TestUtil.sameInstant;
import static com.books.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ReadingResource} REST controller.
 */
@SpringBootTest(classes = BooksApp.class)
public class ReadingResourceIT {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private ReadingService readingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReadingMockMvc;

    private Reading reading;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReadingResource readingResource = new ReadingResource(readingService);
        this.restReadingMockMvc = MockMvcBuilders.standaloneSetup(readingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reading createEntity(EntityManager em) {
        Reading reading = new Reading()
            .page(DEFAULT_PAGE)
            .createTime(DEFAULT_CREATE_TIME);
        return reading;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reading createUpdatedEntity(EntityManager em) {
        Reading reading = new Reading()
            .page(UPDATED_PAGE)
            .createTime(UPDATED_CREATE_TIME);
        return reading;
    }

    @BeforeEach
    public void initTest() {
        reading = createEntity(em);
    }

    @Test
    @Transactional
    public void createReading() throws Exception {
        int databaseSizeBeforeCreate = readingRepository.findAll().size();

        // Create the Reading
        restReadingMockMvc.perform(post("/api/readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reading)))
            .andExpect(status().isCreated());

        // Validate the Reading in the database
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeCreate + 1);
        Reading testReading = readingList.get(readingList.size() - 1);
        assertThat(testReading.getPage()).isEqualTo(DEFAULT_PAGE);
        assertThat(testReading.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createReadingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = readingRepository.findAll().size();

        // Create the Reading with an existing ID
        reading.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReadingMockMvc.perform(post("/api/readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reading)))
            .andExpect(status().isBadRequest());

        // Validate the Reading in the database
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReadings() throws Exception {
        // Initialize the database
        readingRepository.saveAndFlush(reading);

        // Get all the readingList
        restReadingMockMvc.perform(get("/api/readings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reading.getId().intValue())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getReading() throws Exception {
        // Initialize the database
        readingRepository.saveAndFlush(reading);

        // Get the reading
        restReadingMockMvc.perform(get("/api/readings/{id}", reading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reading.getId().intValue()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingReading() throws Exception {
        // Get the reading
        restReadingMockMvc.perform(get("/api/readings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReading() throws Exception {
        // Initialize the database
        readingService.save(reading);

        int databaseSizeBeforeUpdate = readingRepository.findAll().size();

        // Update the reading
        Reading updatedReading = readingRepository.findById(reading.getId()).get();
        // Disconnect from session so that the updates on updatedReading are not directly saved in db
        em.detach(updatedReading);
        updatedReading
            .page(UPDATED_PAGE)
            .createTime(UPDATED_CREATE_TIME);

        restReadingMockMvc.perform(put("/api/readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReading)))
            .andExpect(status().isOk());

        // Validate the Reading in the database
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeUpdate);
        Reading testReading = readingList.get(readingList.size() - 1);
        assertThat(testReading.getPage()).isEqualTo(UPDATED_PAGE);
        assertThat(testReading.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingReading() throws Exception {
        int databaseSizeBeforeUpdate = readingRepository.findAll().size();

        // Create the Reading

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReadingMockMvc.perform(put("/api/readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reading)))
            .andExpect(status().isBadRequest());

        // Validate the Reading in the database
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReading() throws Exception {
        // Initialize the database
        readingService.save(reading);

        int databaseSizeBeforeDelete = readingRepository.findAll().size();

        // Delete the reading
        restReadingMockMvc.perform(delete("/api/readings/{id}", reading.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reading> readingList = readingRepository.findAll();
        assertThat(readingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reading.class);
        Reading reading1 = new Reading();
        reading1.setId(1L);
        Reading reading2 = new Reading();
        reading2.setId(reading1.getId());
        assertThat(reading1).isEqualTo(reading2);
        reading2.setId(2L);
        assertThat(reading1).isNotEqualTo(reading2);
        reading1.setId(null);
        assertThat(reading1).isNotEqualTo(reading2);
    }
}
