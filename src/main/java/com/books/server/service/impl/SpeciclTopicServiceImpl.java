package com.books.server.service.impl;

import com.books.server.service.SpeciclTopicService;
import com.books.server.domain.SpeciclTopic;
import com.books.server.repository.SpeciclTopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SpeciclTopic}.
 */
@Service
@Transactional
public class SpeciclTopicServiceImpl implements SpeciclTopicService {

    private final Logger log = LoggerFactory.getLogger(SpeciclTopicServiceImpl.class);

    private final SpeciclTopicRepository speciclTopicRepository;

    public SpeciclTopicServiceImpl(SpeciclTopicRepository speciclTopicRepository) {
        this.speciclTopicRepository = speciclTopicRepository;
    }

    /**
     * Save a speciclTopic.
     *
     * @param speciclTopic the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SpeciclTopic save(SpeciclTopic speciclTopic) {
        log.debug("Request to save SpeciclTopic : {}", speciclTopic);
        return speciclTopicRepository.save(speciclTopic);
    }

    /**
     * Get all the speciclTopics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SpeciclTopic> findAll(Pageable pageable) {
        log.debug("Request to get all SpeciclTopics");
        return speciclTopicRepository.findAll(pageable);
    }


    /**
     * Get one speciclTopic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpeciclTopic> findOne(Long id) {
        log.debug("Request to get SpeciclTopic : {}", id);
        return speciclTopicRepository.findById(id);
    }

    /**
     * Delete the speciclTopic by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SpeciclTopic : {}", id);
        speciclTopicRepository.deleteById(id);
    }
}
