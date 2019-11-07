package com.books.server.service;

import com.books.server.domain.BookSummary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BookSummary}.
 */
public interface BookSummaryService {

    /**
     * Save a bookSummary.
     *
     * @param bookSummary the entity to save.
     * @return the persisted entity.
     */
    BookSummary save(BookSummary bookSummary);

    /**
     * Get all the bookSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookSummary> findAll(Pageable pageable);


    /**
     * Get the "id" bookSummary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookSummary> findOne(Long id);

    /**
     * Delete the "id" bookSummary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
