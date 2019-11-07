package com.books.server.web.rest;

import com.books.server.BooksApp;
import com.books.server.domain.Square;
import com.books.server.repository.SquareRepository;
import com.books.server.service.SquareService;
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
 * Integration tests for the {@Link SquareResource} REST controller.
 */
@SpringBootTest(classes = BooksApp.class)
public class SquareResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_BOOK_SAYING = "AAAAAAAAAA";
    private static final String UPDATED_BOOK_SAYING = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SquareRepository squareRepository;

    @Autowired
    private SquareService squareService;

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

    private MockMvc restSquareMockMvc;

    private Square square;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SquareResource squareResource = new SquareResource(squareService);
        this.restSquareMockMvc = MockMvcBuilders.standaloneSetup(squareResource)
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
    public static Square createEntity(EntityManager em) {
        Square square = new Square()
            .userId(DEFAULT_USER_ID)
            .bookSaying(DEFAULT_BOOK_SAYING)
            .comment(DEFAULT_COMMENT)
            .createTime(DEFAULT_CREATE_TIME);
        return square;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Square createUpdatedEntity(EntityManager em) {
        Square square = new Square()
            .userId(UPDATED_USER_ID)
            .bookSaying(UPDATED_BOOK_SAYING)
            .comment(UPDATED_COMMENT)
            .createTime(UPDATED_CREATE_TIME);
        return square;
    }

    @BeforeEach
    public void initTest() {
        square = createEntity(em);
    }

    @Test
    @Transactional
    public void createSquare() throws Exception {
        int databaseSizeBeforeCreate = squareRepository.findAll().size();

        // Create the Square
        restSquareMockMvc.perform(post("/api/squares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(square)))
            .andExpect(status().isCreated());

        // Validate the Square in the database
        List<Square> squareList = squareRepository.findAll();
        assertThat(squareList).hasSize(databaseSizeBeforeCreate + 1);
        Square testSquare = squareList.get(squareList.size() - 1);
        assertThat(testSquare.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSquare.getBookSaying()).isEqualTo(DEFAULT_BOOK_SAYING);
        assertThat(testSquare.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testSquare.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createSquareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = squareRepository.findAll().size();

        // Create the Square with an existing ID
        square.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSquareMockMvc.perform(post("/api/squares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(square)))
            .andExpect(status().isBadRequest());

        // Validate the Square in the database
        List<Square> squareList = squareRepository.findAll();
        assertThat(squareList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSquares() throws Exception {
        // Initialize the database
        squareRepository.saveAndFlush(square);

        // Get all the squareList
        restSquareMockMvc.perform(get("/api/squares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(square.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].bookSaying").value(hasItem(DEFAULT_BOOK_SAYING.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getSquare() throws Exception {
        // Initialize the database
        squareRepository.saveAndFlush(square);

        // Get the square
        restSquareMockMvc.perform(get("/api/squares/{id}", square.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(square.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.bookSaying").value(DEFAULT_BOOK_SAYING.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingSquare() throws Exception {
        // Get the square
        restSquareMockMvc.perform(get("/api/squares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSquare() throws Exception {
        // Initialize the database
        squareService.save(square);

        int databaseSizeBeforeUpdate = squareRepository.findAll().size();

        // Update the square
        Square updatedSquare = squareRepository.findById(square.getId()).get();
        // Disconnect from session so that the updates on updatedSquare are not directly saved in db
        em.detach(updatedSquare);
        updatedSquare
            .userId(UPDATED_USER_ID)
            .bookSaying(UPDATED_BOOK_SAYING)
            .comment(UPDATED_COMMENT)
            .createTime(UPDATED_CREATE_TIME);

        restSquareMockMvc.perform(put("/api/squares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSquare)))
            .andExpect(status().isOk());

        // Validate the Square in the database
        List<Square> squareList = squareRepository.findAll();
        assertThat(squareList).hasSize(databaseSizeBeforeUpdate);
        Square testSquare = squareList.get(squareList.size() - 1);
        assertThat(testSquare.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSquare.getBookSaying()).isEqualTo(UPDATED_BOOK_SAYING);
        assertThat(testSquare.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testSquare.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSquare() throws Exception {
        int databaseSizeBeforeUpdate = squareRepository.findAll().size();

        // Create the Square

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSquareMockMvc.perform(put("/api/squares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(square)))
            .andExpect(status().isBadRequest());

        // Validate the Square in the database
        List<Square> squareList = squareRepository.findAll();
        assertThat(squareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSquare() throws Exception {
        // Initialize the database
        squareService.save(square);

        int databaseSizeBeforeDelete = squareRepository.findAll().size();

        // Delete the square
        restSquareMockMvc.perform(delete("/api/squares/{id}", square.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Square> squareList = squareRepository.findAll();
        assertThat(squareList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Square.class);
        Square square1 = new Square();
        square1.setId(1L);
        Square square2 = new Square();
        square2.setId(square1.getId());
        assertThat(square1).isEqualTo(square2);
        square2.setId(2L);
        assertThat(square1).isNotEqualTo(square2);
        square1.setId(null);
        assertThat(square1).isNotEqualTo(square2);
    }
}
