package com.books.server.web.rest;

import com.books.server.BooksApp;
import com.books.server.domain.SpeciclTopic;
import com.books.server.repository.SpeciclTopicRepository;
import com.books.server.service.SpeciclTopicService;
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
 * Integration tests for the {@Link SpeciclTopicResource} REST controller.
 */
@SpringBootTest(classes = BooksApp.class)
public class SpeciclTopicResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SpeciclTopicRepository speciclTopicRepository;

    @Autowired
    private SpeciclTopicService speciclTopicService;

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

    private MockMvc restSpeciclTopicMockMvc;

    private SpeciclTopic speciclTopic;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpeciclTopicResource speciclTopicResource = new SpeciclTopicResource(speciclTopicService);
        this.restSpeciclTopicMockMvc = MockMvcBuilders.standaloneSetup(speciclTopicResource)
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
    public static SpeciclTopic createEntity(EntityManager em) {
        SpeciclTopic speciclTopic = new SpeciclTopic()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .image(DEFAULT_IMAGE)
            .createTime(DEFAULT_CREATE_TIME);
        return speciclTopic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpeciclTopic createUpdatedEntity(EntityManager em) {
        SpeciclTopic speciclTopic = new SpeciclTopic()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .image(UPDATED_IMAGE)
            .createTime(UPDATED_CREATE_TIME);
        return speciclTopic;
    }

    @BeforeEach
    public void initTest() {
        speciclTopic = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeciclTopic() throws Exception {
        int databaseSizeBeforeCreate = speciclTopicRepository.findAll().size();

        // Create the SpeciclTopic
        restSpeciclTopicMockMvc.perform(post("/api/specicl-topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciclTopic)))
            .andExpect(status().isCreated());

        // Validate the SpeciclTopic in the database
        List<SpeciclTopic> speciclTopicList = speciclTopicRepository.findAll();
        assertThat(speciclTopicList).hasSize(databaseSizeBeforeCreate + 1);
        SpeciclTopic testSpeciclTopic = speciclTopicList.get(speciclTopicList.size() - 1);
        assertThat(testSpeciclTopic.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSpeciclTopic.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testSpeciclTopic.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testSpeciclTopic.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createSpeciclTopicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = speciclTopicRepository.findAll().size();

        // Create the SpeciclTopic with an existing ID
        speciclTopic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeciclTopicMockMvc.perform(post("/api/specicl-topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciclTopic)))
            .andExpect(status().isBadRequest());

        // Validate the SpeciclTopic in the database
        List<SpeciclTopic> speciclTopicList = speciclTopicRepository.findAll();
        assertThat(speciclTopicList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSpeciclTopics() throws Exception {
        // Initialize the database
        speciclTopicRepository.saveAndFlush(speciclTopic);

        // Get all the speciclTopicList
        restSpeciclTopicMockMvc.perform(get("/api/specicl-topics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speciclTopic.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getSpeciclTopic() throws Exception {
        // Initialize the database
        speciclTopicRepository.saveAndFlush(speciclTopic);

        // Get the speciclTopic
        restSpeciclTopicMockMvc.perform(get("/api/specicl-topics/{id}", speciclTopic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(speciclTopic.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingSpeciclTopic() throws Exception {
        // Get the speciclTopic
        restSpeciclTopicMockMvc.perform(get("/api/specicl-topics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeciclTopic() throws Exception {
        // Initialize the database
        speciclTopicService.save(speciclTopic);

        int databaseSizeBeforeUpdate = speciclTopicRepository.findAll().size();

        // Update the speciclTopic
        SpeciclTopic updatedSpeciclTopic = speciclTopicRepository.findById(speciclTopic.getId()).get();
        // Disconnect from session so that the updates on updatedSpeciclTopic are not directly saved in db
        em.detach(updatedSpeciclTopic);
        updatedSpeciclTopic
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .image(UPDATED_IMAGE)
            .createTime(UPDATED_CREATE_TIME);

        restSpeciclTopicMockMvc.perform(put("/api/specicl-topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpeciclTopic)))
            .andExpect(status().isOk());

        // Validate the SpeciclTopic in the database
        List<SpeciclTopic> speciclTopicList = speciclTopicRepository.findAll();
        assertThat(speciclTopicList).hasSize(databaseSizeBeforeUpdate);
        SpeciclTopic testSpeciclTopic = speciclTopicList.get(speciclTopicList.size() - 1);
        assertThat(testSpeciclTopic.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSpeciclTopic.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testSpeciclTopic.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testSpeciclTopic.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSpeciclTopic() throws Exception {
        int databaseSizeBeforeUpdate = speciclTopicRepository.findAll().size();

        // Create the SpeciclTopic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeciclTopicMockMvc.perform(put("/api/specicl-topics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(speciclTopic)))
            .andExpect(status().isBadRequest());

        // Validate the SpeciclTopic in the database
        List<SpeciclTopic> speciclTopicList = speciclTopicRepository.findAll();
        assertThat(speciclTopicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpeciclTopic() throws Exception {
        // Initialize the database
        speciclTopicService.save(speciclTopic);

        int databaseSizeBeforeDelete = speciclTopicRepository.findAll().size();

        // Delete the speciclTopic
        restSpeciclTopicMockMvc.perform(delete("/api/specicl-topics/{id}", speciclTopic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpeciclTopic> speciclTopicList = speciclTopicRepository.findAll();
        assertThat(speciclTopicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpeciclTopic.class);
        SpeciclTopic speciclTopic1 = new SpeciclTopic();
        speciclTopic1.setId(1L);
        SpeciclTopic speciclTopic2 = new SpeciclTopic();
        speciclTopic2.setId(speciclTopic1.getId());
        assertThat(speciclTopic1).isEqualTo(speciclTopic2);
        speciclTopic2.setId(2L);
        assertThat(speciclTopic1).isNotEqualTo(speciclTopic2);
        speciclTopic1.setId(null);
        assertThat(speciclTopic1).isNotEqualTo(speciclTopic2);
    }
}
