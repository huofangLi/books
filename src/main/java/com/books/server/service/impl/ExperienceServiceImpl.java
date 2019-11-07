package com.books.server.service.impl;

import com.books.server.service.ExperienceService;
import com.books.server.domain.Experience;
import com.books.server.repository.ExperienceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Experience}.
 */
@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private final Logger log = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private final ExperienceRepository experienceRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    /**
     * Save a experience.
     *
     * @param experience the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Experience save(Experience experience) {
        log.debug("Request to save Experience : {}", experience);
        return experienceRepository.save(experience);
    }

    /**
     * Get all the experiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Experience> findAll(Pageable pageable) {
        log.debug("Request to get all Experiences");
        return experienceRepository.findAll(pageable);
    }


    /**
     * Get one experience by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Experience> findOne(Long id) {
        log.debug("Request to get Experience : {}", id);
        return experienceRepository.findById(id);
    }

    /**
     * Delete the experience by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Experience : {}", id);
        experienceRepository.deleteById(id);
    }
}
