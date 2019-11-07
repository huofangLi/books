package com.books.server.web.rest;

import com.books.server.domain.BookClassification;
import com.books.server.service.BookClassificationService;
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
 * REST controller for managing {@link com.books.server.domain.BookClassification}.
 */
@RestController
@RequestMapping("/api")
public class BookClassificationResource {

    private final Logger log = LoggerFactory.getLogger(BookClassificationResource.class);

    private static final String ENTITY_NAME = "bookClassification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookClassificationService bookClassificationService;

    public BookClassificationResource(BookClassificationService bookClassificationService) {
        this.bookClassificationService = bookClassificationService;
    }

    /**
     * {@code POST  /book-classifications} : Create a new bookClassification.
     *
     * @param bookClassification the bookClassification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookClassification, or with status {@code 400 (Bad Request)} if the bookClassification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/book-classifications")
    public ResponseEntity<BookClassification> createBookClassification(@RequestBody BookClassification bookClassification) throws URISyntaxException {
        log.debug("REST request to save BookClassification : {}", bookClassification);
        if (bookClassification.getId() != null) {
            throw new BadRequestAlertException("A new bookClassification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookClassification result = bookClassificationService.save(bookClassification);
        return ResponseEntity.created(new URI("/api/book-classifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /book-classifications} : Updates an existing bookClassification.
     *
     * @param bookClassification the bookClassification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookClassification,
     * or with status {@code 400 (Bad Request)} if the bookClassification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookClassification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/book-classifications")
    public ResponseEntity<BookClassification> updateBookClassification(@RequestBody BookClassification bookClassification) throws URISyntaxException {
        log.debug("REST request to update BookClassification : {}", bookClassification);
        if (bookClassification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookClassification result = bookClassificationService.save(bookClassification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookClassification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /book-classifications} : get all the bookClassifications.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookClassifications in body.
     */
    @GetMapping("/book-classifications")
    public ResponseEntity<List<BookClassification>> getAllBookClassifications(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of BookClassifications");
        Page<BookClassification> page = bookClassificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /book-classifications/:id} : get the "id" bookClassification.
     *
     * @param id the id of the bookClassification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookClassification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/book-classifications/{id}")
    public ResponseEntity<BookClassification> getBookClassification(@PathVariable Long id) {
        log.debug("REST request to get BookClassification : {}", id);
        Optional<BookClassification> bookClassification = bookClassificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookClassification);
    }

    /**
     * {@code DELETE  /book-classifications/:id} : delete the "id" bookClassification.
     *
     * @param id the id of the bookClassification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/book-classifications/{id}")
    public ResponseEntity<Void> deleteBookClassification(@PathVariable Long id) {
        log.debug("REST request to delete BookClassification : {}", id);
        bookClassificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
