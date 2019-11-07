package com.books.server.web.rest;

import com.books.server.BooksApp;
import com.books.server.domain.BookClassification;
import com.books.server.repository.BookClassificationRepository;
import com.books.server.service.BookClassificationService;
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
import java.util.List;

import static com.books.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link BookClassificationResource} REST controller.
 */
@SpringBootTest(classes = BooksApp.class)
public class BookClassificationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BookClassificationRepository bookClassificationRepository;

    @Autowired
    private BookClassificationService bookClassificationService;

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

    private MockMvc restBookClassificationMockMvc;

    private BookClassification bookClassification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookClassificationResource bookClassificationResource = new BookClassificationResource(bookClassificationService);
        this.restBookClassificationMockMvc = MockMvcBuilders.standaloneSetup(bookClassificationResource)
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
    public static BookClassification createEntity(EntityManager em) {
        BookClassification bookClassification = new BookClassification()
            .name(DEFAULT_NAME);
        return bookClassification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookClassification createUpdatedEntity(EntityManager em) {
        BookClassification bookClassification = new BookClassification()
            .name(UPDATED_NAME);
        return bookClassification;
    }

    @BeforeEach
    public void initTest() {
        bookClassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookClassification() throws Exception {
        int databaseSizeBeforeCreate = bookClassificationRepository.findAll().size();

        // Create the BookClassification
        restBookClassificationMockMvc.perform(post("/api/book-classifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookClassification)))
            .andExpect(status().isCreated());

        // Validate the BookClassification in the database
        List<BookClassification> bookClassificationList = bookClassificationRepository.findAll();
        assertThat(bookClassificationList).hasSize(databaseSizeBeforeCreate + 1);
        BookClassification testBookClassification = bookClassificationList.get(bookClassificationList.size() - 1);
        assertThat(testBookClassification.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBookClassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookClassificationRepository.findAll().size();

        // Create the BookClassification with an existing ID
        bookClassification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookClassificationMockMvc.perform(post("/api/book-classifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookClassification)))
            .andExpect(status().isBadRequest());

        // Validate the BookClassification in the database
        List<BookClassification> bookClassificationList = bookClassificationRepository.findAll();
        assertThat(bookClassificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBookClassifications() throws Exception {
        // Initialize the database
        bookClassificationRepository.saveAndFlush(bookClassification);

        // Get all the bookClassificationList
        restBookClassificationMockMvc.perform(get("/api/book-classifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookClassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getBookClassification() throws Exception {
        // Initialize the database
        bookClassificationRepository.saveAndFlush(bookClassification);

        // Get the bookClassification
        restBookClassificationMockMvc.perform(get("/api/book-classifications/{id}", bookClassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookClassification.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBookClassification() throws Exception {
        // Get the bookClassification
        restBookClassificationMockMvc.perform(get("/api/book-classifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookClassification() throws Exception {
        // Initialize the database
        bookClassificationService.save(bookClassification);

        int databaseSizeBeforeUpdate = bookClassificationRepository.findAll().size();

        // Update the bookClassification
        BookClassification updatedBookClassification = bookClassificationRepository.findById(bookClassification.getId()).get();
        // Disconnect from session so that the updates on updatedBookClassification are not directly saved in db
        em.detach(updatedBookClassification);
        updatedBookClassification
            .name(UPDATED_NAME);

        restBookClassificationMockMvc.perform(put("/api/book-classifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookClassification)))
            .andExpect(status().isOk());

        // Validate the BookClassification in the database
        List<BookClassification> bookClassificationList = bookClassificationRepository.findAll();
        assertThat(bookClassificationList).hasSize(databaseSizeBeforeUpdate);
        BookClassification testBookClassification = bookClassificationList.get(bookClassificationList.size() - 1);
        assertThat(testBookClassification.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBookClassification() throws Exception {
        int databaseSizeBeforeUpdate = bookClassificationRepository.findAll().size();

        // Create the BookClassification

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookClassificationMockMvc.perform(put("/api/book-classifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookClassification)))
            .andExpect(status().isBadRequest());

        // Validate the BookClassification in the database
        List<BookClassification> bookClassificationList = bookClassificationRepository.findAll();
        assertThat(bookClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookClassification() throws Exception {
        // Initialize the database
        bookClassificationService.save(bookClassification);

        int databaseSizeBeforeDelete = bookClassificationRepository.findAll().size();

        // Delete the bookClassification
        restBookClassificationMockMvc.perform(delete("/api/book-classifications/{id}", bookClassification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookClassification> bookClassificationList = bookClassificationRepository.findAll();
        assertThat(bookClassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookClassification.class);
        BookClassification bookClassification1 = new BookClassification();
        bookClassification1.setId(1L);
        BookClassification bookClassification2 = new BookClassification();
        bookClassification2.setId(bookClassification1.getId());
        assertThat(bookClassification1).isEqualTo(bookClassification2);
        bookClassification2.setId(2L);
        assertThat(bookClassification1).isNotEqualTo(bookClassification2);
        bookClassification1.setId(null);
        assertThat(bookClassification1).isNotEqualTo(bookClassification2);
    }
}
