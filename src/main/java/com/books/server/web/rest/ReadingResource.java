package com.books.server.web.rest;

import com.books.server.domain.Reading;
import com.books.server.service.ReadingService;
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
 * REST controller for managing {@link com.books.server.domain.Reading}.
 */
@RestController
@RequestMapping("/api")
public class ReadingResource {

    private final Logger log = LoggerFactory.getLogger(ReadingResource.class);

    private static final String ENTITY_NAME = "reading";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReadingService readingService;

    public ReadingResource(ReadingService readingService) {
        this.readingService = readingService;
    }

    /**
     * {@code POST  /readings} : Create a new reading.
     *
     * @param reading the reading to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reading, or with status {@code 400 (Bad Request)} if the reading has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/readings")
    public ResponseEntity<Reading> createReading(@RequestBody Reading reading) throws URISyntaxException {
        log.debug("REST request to save Reading : {}", reading);
        if (reading.getId() != null) {
            throw new BadRequestAlertException("A new reading cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reading result = readingService.save(reading);
        return ResponseEntity.created(new URI("/api/readings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /readings} : Updates an existing reading.
     *
     * @param reading the reading to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reading,
     * or with status {@code 400 (Bad Request)} if the reading is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reading couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/readings")
    public ResponseEntity<Reading> updateReading(@RequestBody Reading reading) throws URISyntaxException {
        log.debug("REST request to update Reading : {}", reading);
        if (reading.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reading result = readingService.save(reading);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reading.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /readings} : get all the readings.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of readings in body.
     */
    @GetMapping("/readings")
    public ResponseEntity<List<Reading>> getAllReadings(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Readings");
        Page<Reading> page = readingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /readings/:id} : get the "id" reading.
     *
     * @param id the id of the reading to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reading, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/readings/{id}")
    public ResponseEntity<Reading> getReading(@PathVariable Long id) {
        log.debug("REST request to get Reading : {}", id);
        Optional<Reading> reading = readingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reading);
    }

    /**
     * {@code DELETE  /readings/:id} : delete the "id" reading.
     *
     * @param id the id of the reading to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/readings/{id}")
    public ResponseEntity<Void> deleteReading(@PathVariable Long id) {
        log.debug("REST request to delete Reading : {}", id);
        readingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
