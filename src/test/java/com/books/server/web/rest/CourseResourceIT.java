package com.books.server.web.rest;

import com.books.server.BooksApp;
import com.books.server.domain.Course;
import com.books.server.repository.CourseRepository;
import com.books.server.service.CourseService;
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
 * Integration tests for the {@Link CourseResource} REST controller.
 */
@SpringBootTest(classes = BooksApp.class)
public class CourseResourceIT {

    private static final String DEFAULT_COURSE_CLASSIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_CLASSIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_DESCRIBE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_DESCRIBE = "BBBBBBBBBB";

    private static final Double DEFAULT_COURSE_PRICE = 1D;
    private static final Double UPDATED_COURSE_PRICE = 2D;

    private static final String DEFAULT_COURSE_CHAPTER = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_CHAPTER = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_VIDEO = "BBBBBBBBBB";

    private static final String DEFAULT_PRESENTER = "AAAAAAAAAA";
    private static final String UPDATED_PRESENTER = "BBBBBBBBBB";

    private static final String DEFAULT_PRESENTER_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_PRESENTER_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PRESENTER_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_PRESENTER_INTRODUCTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

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

    private MockMvc restCourseMockMvc;

    private Course course;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourseResource courseResource = new CourseResource(courseService);
        this.restCourseMockMvc = MockMvcBuilders.standaloneSetup(courseResource)
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
    public static Course createEntity(EntityManager em) {
        Course course = new Course()
            .courseClassification(DEFAULT_COURSE_CLASSIFICATION)
            .courseName(DEFAULT_COURSE_NAME)
            .courseImage(DEFAULT_COURSE_IMAGE)
            .courseDescribe(DEFAULT_COURSE_DESCRIBE)
            .coursePrice(DEFAULT_COURSE_PRICE)
            .courseChapter(DEFAULT_COURSE_CHAPTER)
            .courseIntroduction(DEFAULT_COURSE_INTRODUCTION)
            .courseVideo(DEFAULT_COURSE_VIDEO)
            .presenter(DEFAULT_PRESENTER)
            .presenterImage(DEFAULT_PRESENTER_IMAGE)
            .presenterIntroduction(DEFAULT_PRESENTER_INTRODUCTION)
            .createTime(DEFAULT_CREATE_TIME);
        return course;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Course createUpdatedEntity(EntityManager em) {
        Course course = new Course()
            .courseClassification(UPDATED_COURSE_CLASSIFICATION)
            .courseName(UPDATED_COURSE_NAME)
            .courseImage(UPDATED_COURSE_IMAGE)
            .courseDescribe(UPDATED_COURSE_DESCRIBE)
            .coursePrice(UPDATED_COURSE_PRICE)
            .courseChapter(UPDATED_COURSE_CHAPTER)
            .courseIntroduction(UPDATED_COURSE_INTRODUCTION)
            .courseVideo(UPDATED_COURSE_VIDEO)
            .presenter(UPDATED_PRESENTER)
            .presenterImage(UPDATED_PRESENTER_IMAGE)
            .presenterIntroduction(UPDATED_PRESENTER_INTRODUCTION)
            .createTime(UPDATED_CREATE_TIME);
        return course;
    }

    @BeforeEach
    public void initTest() {
        course = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getCourseClassification()).isEqualTo(DEFAULT_COURSE_CLASSIFICATION);
        assertThat(testCourse.getCourseName()).isEqualTo(DEFAULT_COURSE_NAME);
        assertThat(testCourse.getCourseImage()).isEqualTo(DEFAULT_COURSE_IMAGE);
        assertThat(testCourse.getCourseDescribe()).isEqualTo(DEFAULT_COURSE_DESCRIBE);
        assertThat(testCourse.getCoursePrice()).isEqualTo(DEFAULT_COURSE_PRICE);
        assertThat(testCourse.getCourseChapter()).isEqualTo(DEFAULT_COURSE_CHAPTER);
        assertThat(testCourse.getCourseIntroduction()).isEqualTo(DEFAULT_COURSE_INTRODUCTION);
        assertThat(testCourse.getCourseVideo()).isEqualTo(DEFAULT_COURSE_VIDEO);
        assertThat(testCourse.getPresenter()).isEqualTo(DEFAULT_PRESENTER);
        assertThat(testCourse.getPresenterImage()).isEqualTo(DEFAULT_PRESENTER_IMAGE);
        assertThat(testCourse.getPresenterIntroduction()).isEqualTo(DEFAULT_PRESENTER_INTRODUCTION);
        assertThat(testCourse.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course with an existing ID
        course.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get all the courseList
        restCourseMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(course.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseClassification").value(hasItem(DEFAULT_COURSE_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].courseName").value(hasItem(DEFAULT_COURSE_NAME.toString())))
            .andExpect(jsonPath("$.[*].courseImage").value(hasItem(DEFAULT_COURSE_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].courseDescribe").value(hasItem(DEFAULT_COURSE_DESCRIBE.toString())))
            .andExpect(jsonPath("$.[*].coursePrice").value(hasItem(DEFAULT_COURSE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].courseChapter").value(hasItem(DEFAULT_COURSE_CHAPTER.toString())))
            .andExpect(jsonPath("$.[*].courseIntroduction").value(hasItem(DEFAULT_COURSE_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].courseVideo").value(hasItem(DEFAULT_COURSE_VIDEO.toString())))
            .andExpect(jsonPath("$.[*].presenter").value(hasItem(DEFAULT_PRESENTER.toString())))
            .andExpect(jsonPath("$.[*].presenterImage").value(hasItem(DEFAULT_PRESENTER_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].presenterIntroduction").value(hasItem(DEFAULT_PRESENTER_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(course);

        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", course.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(course.getId().intValue()))
            .andExpect(jsonPath("$.courseClassification").value(DEFAULT_COURSE_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.courseName").value(DEFAULT_COURSE_NAME.toString()))
            .andExpect(jsonPath("$.courseImage").value(DEFAULT_COURSE_IMAGE.toString()))
            .andExpect(jsonPath("$.courseDescribe").value(DEFAULT_COURSE_DESCRIBE.toString()))
            .andExpect(jsonPath("$.coursePrice").value(DEFAULT_COURSE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.courseChapter").value(DEFAULT_COURSE_CHAPTER.toString()))
            .andExpect(jsonPath("$.courseIntroduction").value(DEFAULT_COURSE_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.courseVideo").value(DEFAULT_COURSE_VIDEO.toString()))
            .andExpect(jsonPath("$.presenter").value(DEFAULT_PRESENTER.toString()))
            .andExpect(jsonPath("$.presenterImage").value(DEFAULT_PRESENTER_IMAGE.toString()))
            .andExpect(jsonPath("$.presenterIntroduction").value(DEFAULT_PRESENTER_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourse() throws Exception {
        // Initialize the database
        courseService.save(course);

        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        Course updatedCourse = courseRepository.findById(course.getId()).get();
        // Disconnect from session so that the updates on updatedCourse are not directly saved in db
        em.detach(updatedCourse);
        updatedCourse
            .courseClassification(UPDATED_COURSE_CLASSIFICATION)
            .courseName(UPDATED_COURSE_NAME)
            .courseImage(UPDATED_COURSE_IMAGE)
            .courseDescribe(UPDATED_COURSE_DESCRIBE)
            .coursePrice(UPDATED_COURSE_PRICE)
            .courseChapter(UPDATED_COURSE_CHAPTER)
            .courseIntroduction(UPDATED_COURSE_INTRODUCTION)
            .courseVideo(UPDATED_COURSE_VIDEO)
            .presenter(UPDATED_PRESENTER)
            .presenterImage(UPDATED_PRESENTER_IMAGE)
            .presenterIntroduction(UPDATED_PRESENTER_INTRODUCTION)
            .createTime(UPDATED_CREATE_TIME);

        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourse)))
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        Course testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getCourseClassification()).isEqualTo(UPDATED_COURSE_CLASSIFICATION);
        assertThat(testCourse.getCourseName()).isEqualTo(UPDATED_COURSE_NAME);
        assertThat(testCourse.getCourseImage()).isEqualTo(UPDATED_COURSE_IMAGE);
        assertThat(testCourse.getCourseDescribe()).isEqualTo(UPDATED_COURSE_DESCRIBE);
        assertThat(testCourse.getCoursePrice()).isEqualTo(UPDATED_COURSE_PRICE);
        assertThat(testCourse.getCourseChapter()).isEqualTo(UPDATED_COURSE_CHAPTER);
        assertThat(testCourse.getCourseIntroduction()).isEqualTo(UPDATED_COURSE_INTRODUCTION);
        assertThat(testCourse.getCourseVideo()).isEqualTo(UPDATED_COURSE_VIDEO);
        assertThat(testCourse.getPresenter()).isEqualTo(UPDATED_PRESENTER);
        assertThat(testCourse.getPresenterImage()).isEqualTo(UPDATED_PRESENTER_IMAGE);
        assertThat(testCourse.getPresenterIntroduction()).isEqualTo(UPDATED_PRESENTER_INTRODUCTION);
        assertThat(testCourse.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Create the Course

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(course)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourse() throws Exception {
        // Initialize the database
        courseService.save(course);

        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Delete the course
        restCourseMockMvc.perform(delete("/api/courses/{id}", course.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Course> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Course.class);
        Course course1 = new Course();
        course1.setId(1L);
        Course course2 = new Course();
        course2.setId(course1.getId());
        assertThat(course1).isEqualTo(course2);
        course2.setId(2L);
        assertThat(course1).isNotEqualTo(course2);
        course1.setId(null);
        assertThat(course1).isNotEqualTo(course2);
    }
}
