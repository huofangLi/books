package com.books.server.service.impl;

import com.books.server.service.TopicService;
import com.books.server.domain.Topic;
import com.books.server.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Topic}.
 */
@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    private final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Save a topic.
     *
     * @param topic the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Topic save(Topic topic) {
        log.debug("Request to save Topic : {}", topic);
        return topicRepository.save(topic);
    }

    /**
     * Get all the topics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Topic> findAll(Pageable pageable) {
        log.debug("Request to get all Topics");
        return topicRepository.findAll(pageable);
    }


    /**
     * Get one topic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Topic> findOne(Long id) {
        log.debug("Request to get Topic : {}", id);
        return topicRepository.findById(id);
    }

    /**
     * Delete the topic by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Topic : {}", id);
        topicRepository.deleteById(id);
    }
}
