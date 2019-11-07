package com.books.server.web.rest;

import com.books.server.domain.Square;
import com.books.server.service.SquareService;
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
 * REST controller for managing {@link com.books.server.domain.Square}.
 */
@RestController
@RequestMapping("/api")
public class SquareResource {

    private final Logger log = LoggerFactory.getLogger(SquareResource.class);

    private static final String ENTITY_NAME = "square";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SquareService squareService;

    public SquareResource(SquareService squareService) {
        this.squareService = squareService;
    }

    /**
     * {@code POST  /squares} : Create a new square.
     *
     * @param square the square to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new square, or with status {@code 400 (Bad Request)} if the square has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/squares")
    public ResponseEntity<Square> createSquare(@RequestBody Square square) throws URISyntaxException {
        log.debug("REST request to save Square : {}", square);
        if (square.getId() != null) {
            throw new BadRequestAlertException("A new square cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Square result = squareService.save(square);
        return ResponseEntity.created(new URI("/api/squares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /squares} : Updates an existing square.
     *
     * @param square the square to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated square,
     * or with status {@code 400 (Bad Request)} if the square is not valid,
     * or with status {@code 500 (Internal Server Error)} if the square couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/squares")
    public ResponseEntity<Square> updateSquare(@RequestBody Square square) throws URISyntaxException {
        log.debug("REST request to update Square : {}", square);
        if (square.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Square result = squareService.save(square);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, square.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /squares} : get all the squares.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of squares in body.
     */
    @GetMapping("/squares")
    public ResponseEntity<List<Square>> getAllSquares(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Squares");
        Page<Square> page = squareService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /squares/:id} : get the "id" square.
     *
     * @param id the id of the square to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the square, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/squares/{id}")
    public ResponseEntity<Square> getSquare(@PathVariable Long id) {
        log.debug("REST request to get Square : {}", id);
        Optional<Square> square = squareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(square);
    }

    /**
     * {@code DELETE  /squares/:id} : delete the "id" square.
     *
     * @param id the id of the square to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/squares/{id}")
    public ResponseEntity<Void> deleteSquare(@PathVariable Long id) {
        log.debug("REST request to delete Square : {}", id);
        squareService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
