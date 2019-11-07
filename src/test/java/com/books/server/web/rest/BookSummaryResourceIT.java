package com.books.server.web.rest;

import com.books.server.BooksApp;
import com.books.server.domain.BookSummary;
import com.books.server.repository.BookSummaryRepository;
import com.books.server.service.BookSummaryService;
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
 * Integration tests for the {@Link BookSummaryResource} REST controller.
 */
@SpringBootTest(classes = BooksApp.class)
public class BookSummaryResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BookSummaryRepository bookSummaryRepository;

    @Autowired
    private BookSummaryService bookSummaryService;

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

    private MockMvc restBookSummaryMockMvc;

    private BookSummary bookSummary;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookSummaryResource bookSummaryResource = new BookSummaryResource(bookSummaryService);
        this.restBookSummaryMockMvc = MockMvcBuilders.standaloneSetup(bookSummaryResource)
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
    public static BookSummary createEntity(EntityManager em) {
        BookSummary bookSummary = new BookSummary()
            .comment(DEFAULT_COMMENT)
            .createTime(DEFAULT_CREATE_TIME);
        return bookSummary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookSummary createUpdatedEntity(EntityManager em) {
        BookSummary bookSummary = new BookSummary()
            .comment(UPDATED_COMMENT)
            .createTime(UPDATED_CREATE_TIME);
        return bookSummary;
    }

    @BeforeEach
    public void initTest() {
        bookSummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookSummary() throws Exception {
        int databaseSizeBeforeCreate = bookSummaryRepository.findAll().size();

        // Create the BookSummary
        restBookSummaryMockMvc.perform(post("/api/book-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSummary)))
            .andExpect(status().isCreated());

        // Validate the BookSummary in the database
        List<BookSummary> bookSummaryList = bookSummaryRepository.findAll();
        assertThat(bookSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        BookSummary testBookSummary = bookSummaryList.get(bookSummaryList.size() - 1);
        assertThat(testBookSummary.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testBookSummary.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createBookSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookSummaryRepository.findAll().size();

        // Create the BookSummary with an existing ID
        bookSummary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookSummaryMockMvc.perform(post("/api/book-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSummary)))
            .andExpect(status().isBadRequest());

        // Validate the BookSummary in the database
        List<BookSummary> bookSummaryList = bookSummaryRepository.findAll();
        assertThat(bookSummaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBookSummaries() throws Exception {
        // Initialize the database
        bookSummaryRepository.saveAndFlush(bookSummary);

        // Get all the bookSummaryList
        restBookSummaryMockMvc.perform(get("/api/book-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getBookSummary() throws Exception {
        // Initialize the database
        bookSummaryRepository.saveAndFlush(bookSummary);

        // Get the bookSummary
        restBookSummaryMockMvc.perform(get("/api/book-summaries/{id}", bookSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookSummary.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBookSummary() throws Exception {
        // Get the bookSummary
        restBookSummaryMockMvc.perform(get("/api/book-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookSummary() throws Exception {
        // Initialize the database
        bookSummaryService.save(bookSummary);

        int databaseSizeBeforeUpdate = bookSummaryRepository.findAll().size();

        // Update the bookSummary
        BookSummary updatedBookSummary = bookSummaryRepository.findById(bookSummary.getId()).get();
        // Disconnect from session so that the updates on updatedBookSummary are not directly saved in db
        em.detach(updatedBookSummary);
        updatedBookSummary
            .comment(UPDATED_COMMENT)
            .createTime(UPDATED_CREATE_TIME);

        restBookSummaryMockMvc.perform(put("/api/book-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBookSummary)))
            .andExpect(status().isOk());

        // Validate the BookSummary in the database
        List<BookSummary> bookSummaryList = bookSummaryRepository.findAll();
        assertThat(bookSummaryList).hasSize(databaseSizeBeforeUpdate);
        BookSummary testBookSummary = bookSummaryList.get(bookSummaryList.size() - 1);
        assertThat(testBookSummary.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testBookSummary.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBookSummary() throws Exception {
        int databaseSizeBeforeUpdate = bookSummaryRepository.findAll().size();

        // Create the BookSummary

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookSummaryMockMvc.perform(put("/api/book-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookSummary)))
            .andExpect(status().isBadRequest());

        // Validate the BookSummary in the database
        List<BookSummary> bookSummaryList = bookSummaryRepository.findAll();
        assertThat(bookSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBookSummary() throws Exception {
        // Initialize the database
        bookSummaryService.save(bookSummary);

        int databaseSizeBeforeDelete = bookSummaryRepository.findAll().size();

        // Delete the bookSummary
        restBookSummaryMockMvc.perform(delete("/api/book-summaries/{id}", bookSummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookSummary> bookSummaryList = bookSummaryRepository.findAll();
        assertThat(bookSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookSummary.class);
        BookSummary bookSummary1 = new BookSummary();
        bookSummary1.setId(1L);
        BookSummary bookSummary2 = new BookSummary();
        bookSummary2.setId(bookSummary1.getId());
        assertThat(bookSummary1).isEqualTo(bookSummary2);
        bookSummary2.setId(2L);
        assertThat(bookSummary1).isNotEqualTo(bookSummary2);
        bookSummary1.setId(null);
        assertThat(bookSummary1).isNotEqualTo(bookSummary2);
    }
}
