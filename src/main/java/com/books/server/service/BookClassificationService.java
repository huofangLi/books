package com.books.server.service;

import com.books.server.domain.BookClassification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link BookClassification}.
 */
public interface BookClassificationService {

    /**
     * Save a bookClassification.
     *
     * @param bookClassification the entity to save.
     * @return the persisted entity.
     */
    BookClassification save(BookClassification bookClassification);

    /**
     * Get all the bookClassifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookClassification> findAll(Pageable pageable);


    /**
     * Get the "id" bookClassification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookClassification> findOne(Long id);

    /**
     * Delete the "id" bookClassification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
