package com.books.server.web.rest;

import com.books.server.BooksApp;
import com.books.server.domain.Book;
import com.books.server.repository.BookRepository;
import com.books.server.service.BookService;
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
 * Integration tests for the {@Link BookResource} REST controller.
 */
@SpringBootTest(classes = BooksApp.class)
public class BookResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_BOOK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BOOK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BOOK_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_BOOK_IMAGE = "BBBBBBBBBB";

    private static final Long DEFAULT_BOOK_PAGES = 1L;
    private static final Long UPDATED_BOOK_PAGES = 2L;

    private static final Boolean DEFAULT_IS_BORROW = false;
    private static final Boolean UPDATED_IS_BORROW = true;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

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

    private MockMvc restBookMockMvc;

    private Book book;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookResource bookResource = new BookResource(bookService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
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
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .userId(DEFAULT_USER_ID)
            .bookName(DEFAULT_BOOK_NAME)
            .bookImage(DEFAULT_BOOK_IMAGE)
            .bookPages(DEFAULT_BOOK_PAGES)
            .isBorrow(DEFAULT_IS_BORROW)
            .createTime(DEFAULT_CREATE_TIME);
        return book;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .userId(UPDATED_USER_ID)
            .bookName(UPDATED_BOOK_NAME)
            .bookImage(UPDATED_BOOK_IMAGE)
            .bookPages(UPDATED_BOOK_PAGES)
            .isBorrow(UPDATED_IS_BORROW)
            .createTime(UPDATED_CREATE_TIME);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBook.getBookName()).isEqualTo(DEFAULT_BOOK_NAME);
        assertThat(testBook.getBookImage()).isEqualTo(DEFAULT_BOOK_IMAGE);
        assertThat(testBook.getBookPages()).isEqualTo(DEFAULT_BOOK_PAGES);
        assertThat(testBook.isIsBorrow()).isEqualTo(DEFAULT_IS_BORROW);
        assertThat(testBook.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].bookName").value(hasItem(DEFAULT_BOOK_NAME.toString())))
            .andExpect(jsonPath("$.[*].bookImage").value(hasItem(DEFAULT_BOOK_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].bookPages").value(hasItem(DEFAULT_BOOK_PAGES.intValue())))
            .andExpect(jsonPath("$.[*].isBorrow").value(hasItem(DEFAULT_IS_BORROW.booleanValue())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))));
    }
    
    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.bookName").value(DEFAULT_BOOK_NAME.toString()))
            .andExpect(jsonPath("$.bookImage").value(DEFAULT_BOOK_IMAGE.toString()))
            .andExpect(jsonPath("$.bookPages").value(DEFAULT_BOOK_PAGES.intValue()))
            .andExpect(jsonPath("$.isBorrow").value(DEFAULT_IS_BORROW.booleanValue()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .userId(UPDATED_USER_ID)
            .bookName(UPDATED_BOOK_NAME)
            .bookImage(UPDATED_BOOK_IMAGE)
            .bookPages(UPDATED_BOOK_PAGES)
            .isBorrow(UPDATED_IS_BORROW)
            .createTime(UPDATED_CREATE_TIME);

        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBook)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBook.getBookName()).isEqualTo(UPDATED_BOOK_NAME);
        assertThat(testBook.getBookImage()).isEqualTo(UPDATED_BOOK_IMAGE);
        assertThat(testBook.getBookPages()).isEqualTo(UPDATED_BOOK_PAGES);
        assertThat(testBook.isIsBorrow()).isEqualTo(UPDATED_IS_BORROW);
        assertThat(testBook.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc.perform(put("/api/books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(book)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);
        book2.setId(2L);
        assertThat(book1).isNotEqualTo(book2);
        book1.setId(null);
        assertThat(book1).isNotEqualTo(book2);
    }
}
