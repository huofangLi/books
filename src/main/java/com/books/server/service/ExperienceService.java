package com.books.server.service;

import com.books.server.domain.Experience;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Experience}.
 */
public interface ExperienceService {

    /**
     * Save a experience.
     *
     * @param experience the entity to save.
     * @return the persisted entity.
     */
    Experience save(Experience experience);

    /**
     * Get all the experiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Experience> findAll(Pageable pageable);


    /**
     * Get the "id" experience.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Experience> findOne(Long id);

    /**
     * Delete the "id" experience.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
