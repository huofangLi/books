package com.books.server.web.rest;

import com.books.server.domain.SpeciclTopic;
import com.books.server.service.SpeciclTopicService;
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
 * REST controller for managing {@link com.books.server.domain.SpeciclTopic}.
 */
@RestController
@RequestMapping("/api")
public class SpeciclTopicResource {

    private final Logger log = LoggerFactory.getLogger(SpeciclTopicResource.class);

    private static final String ENTITY_NAME = "speciclTopic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpeciclTopicService speciclTopicService;

    public SpeciclTopicResource(SpeciclTopicService speciclTopicService) {
        this.speciclTopicService = speciclTopicService;
    }

    /**
     * {@code POST  /specicl-topics} : Create a new speciclTopic.
     *
     * @param speciclTopic the speciclTopic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speciclTopic, or with status {@code 400 (Bad Request)} if the speciclTopic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specicl-topics")
    public ResponseEntity<SpeciclTopic> createSpeciclTopic(@RequestBody SpeciclTopic speciclTopic) throws URISyntaxException {
        log.debug("REST request to save SpeciclTopic : {}", speciclTopic);
        if (speciclTopic.getId() != null) {
            throw new BadRequestAlertException("A new speciclTopic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpeciclTopic result = speciclTopicService.save(speciclTopic);
        return ResponseEntity.created(new URI("/api/specicl-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specicl-topics} : Updates an existing speciclTopic.
     *
     * @param speciclTopic the speciclTopic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speciclTopic,
     * or with status {@code 400 (Bad Request)} if the speciclTopic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speciclTopic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specicl-topics")
    public ResponseEntity<SpeciclTopic> updateSpeciclTopic(@RequestBody SpeciclTopic speciclTopic) throws URISyntaxException {
        log.debug("REST request to update SpeciclTopic : {}", speciclTopic);
        if (speciclTopic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpeciclTopic result = speciclTopicService.save(speciclTopic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speciclTopic.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specicl-topics} : get all the speciclTopics.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of speciclTopics in body.
     */
    @GetMapping("/specicl-topics")
    public ResponseEntity<List<SpeciclTopic>> getAllSpeciclTopics(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of SpeciclTopics");
        Page<SpeciclTopic> page = speciclTopicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specicl-topics/:id} : get the "id" speciclTopic.
     *
     * @param id the id of the speciclTopic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speciclTopic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specicl-topics/{id}")
    public ResponseEntity<SpeciclTopic> getSpeciclTopic(@PathVariable Long id) {
        log.debug("REST request to get SpeciclTopic : {}", id);
        Optional<SpeciclTopic> speciclTopic = speciclTopicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(speciclTopic);
    }

    /**
     * {@code DELETE  /specicl-topics/:id} : delete the "id" speciclTopic.
     *
     * @param id the id of the speciclTopic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specicl-topics/{id}")
    public ResponseEntity<Void> deleteSpeciclTopic(@PathVariable Long id) {
        log.debug("REST request to delete SpeciclTopic : {}", id);
        speciclTopicService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
