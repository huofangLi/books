package com.books.server.service;

import com.books.server.domain.SpeciclTopic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SpeciclTopic}.
 */
public interface SpeciclTopicService {

    /**
     * Save a speciclTopic.
     *
     * @param speciclTopic the entity to save.
     * @return the persisted entity.
     */
    SpeciclTopic save(SpeciclTopic speciclTopic);

    /**
     * Get all the speciclTopics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpeciclTopic> findAll(Pageable pageable);


    /**
     * Get the "id" speciclTopic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpeciclTopic> findOne(Long id);

    /**
     * Delete the "id" speciclTopic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
