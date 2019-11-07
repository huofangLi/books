package com.books.server.web.rest;

import com.books.server.domain.BookSummary;
import com.books.server.service.BookSummaryService;
import com.books.server.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.books.server.domain.BookSummary}.
 */
@RestController
@RequestMapping("/api")
public class BookSummaryResource {

    private final Logger log = LoggerFactory.getLogger(BookSummaryResource.class);

    private static final String ENTITY_NAME = "bookSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookSummaryService bookSummaryService;

    public BookSummaryResource(BookSummaryService bookSummaryService) {
        this.bookSummaryService = bookSummaryService;
    }

    /**
     * {@code POST  /book-summaries} : Create a new bookSummary.
     *
     * @param bookSummary the bookSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookSummary, or with status {@code 400 (Bad Request)} if the bookSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/book-summaries")
    public ResponseEntity<BookSummary> createBookSummary(@RequestBody BookSummary bookSummary) throws URISyntaxException {
        log.debug("REST request to save BookSummary : {}", bookSummary);
        if (bookSummary.getId() != null) {
            throw new BadRequestAlertException("A new bookSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookSummary result = bookSummaryService.save(bookSummary);
        return ResponseEntity.created(new URI("/api/book-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /book-summaries} : Updates an existing bookSummary.
     *
     * @param bookSummary the bookSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookSummary,
     * or with status {@code 400 (Bad Request)} if the bookSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/book-summaries")
    public ResponseEntity<BookSummary> updateBookSummary(@RequestBody BookSummary bookSummary) throws URISyntaxException {
        log.debug("REST request to update BookSummary : {}", bookSummary);
        if (bookSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookSummary result = bookSummaryService.save(bookSummary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /book-summaries} : get all the bookSummaries.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookSummaries in body.
     */
    @GetMapping("/book-summaries")
    public ResponseEntity<List<BookSummary>> getAllBookSummaries(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of BookSummaries");
        Page<BookSummary> page = bookSummaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /book-summaries/:id} : get the "id" bookSummary.
     *
     * @param id the id of the bookSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/book-summaries/{id}")
    public ResponseEntity<BookSummary> getBookSummary(@PathVariable Long id) {
        log.debug("REST request to get BookSummary : {}", id);
        Optional<BookSummary> bookSummary = bookSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookSummary);
    }

    /**
     * {@code DELETE  /book-summaries/:id} : delete the "id" bookSummary.
     *
     * @param id the id of the bookSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/book-summaries/{id}")
    public ResponseEntity<Void> deleteBookSummary(@PathVariable Long id) {
        log.debug("REST request to delete BookSummary : {}", id);
        bookSummaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
