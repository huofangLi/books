package com.books.server.service;

import com.books.server.domain.Reading;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Reading}.
 */
public interface ReadingService {

    /**
     * Save a reading.
     *
     * @param reading the entity to save.
     * @return the persisted entity.
     */
    Reading save(Reading reading);

    /**
     * Get all the readings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Reading> findAll(Pageable pageable);


    /**
     * Get the "id" reading.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Reading> findOne(Long id);

    /**
     * Delete the "id" reading.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
