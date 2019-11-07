package com.books.server.service.impl;

import com.books.server.service.ReadingService;
import com.books.server.domain.Reading;
import com.books.server.repository.ReadingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Reading}.
 */
@Service
@Transactional
public class ReadingServiceImpl implements ReadingService {

    private final Logger log = LoggerFactory.getLogger(ReadingServiceImpl.class);

    private final ReadingRepository readingRepository;

    public ReadingServiceImpl(ReadingRepository readingRepository) {
        this.readingRepository = readingRepository;
    }

    /**
     * Save a reading.
     *
     * @param reading the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Reading save(Reading reading) {
        log.debug("Request to save Reading : {}", reading);
        return readingRepository.save(reading);
    }

    /**
     * Get all the readings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reading> findAll(Pageable pageable) {
        log.debug("Request to get all Readings");
        return readingRepository.findAll(pageable);
    }


    /**
     * Get one reading by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Reading> findOne(Long id) {
        log.debug("Request to get Reading : {}", id);
        return readingRepository.findById(id);
    }

    /**
     * Delete the reading by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reading : {}", id);
        readingRepository.deleteById(id);
    }
}
