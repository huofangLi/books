package com.books.server.service.impl;

import com.books.server.service.BookClassificationService;
import com.books.server.domain.BookClassification;
import com.books.server.repository.BookClassificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BookClassification}.
 */
@Service
@Transactional
public class BookClassificationServiceImpl implements BookClassificationService {

    private final Logger log = LoggerFactory.getLogger(BookClassificationServiceImpl.class);

    private final BookClassificationRepository bookClassificationRepository;

    public BookClassificationServiceImpl(BookClassificationRepository bookClassificationRepository) {
        this.bookClassificationRepository = bookClassificationRepository;
    }

    /**
     * Save a bookClassification.
     *
     * @param bookClassification the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BookClassification save(BookClassification bookClassification) {
        log.debug("Request to save BookClassification : {}", bookClassification);
        return bookClassificationRepository.save(bookClassification);
    }

    /**
     * Get all the bookClassifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookClassification> findAll(Pageable pageable) {
        log.debug("Request to get all BookClassifications");
        return bookClassificationRepository.findAll(pageable);
    }


    /**
     * Get one bookClassification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookClassification> findOne(Long id) {
        log.debug("Request to get BookClassification : {}", id);
        return bookClassificationRepository.findById(id);
    }

    /**
     * Delete the bookClassification by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookClassification : {}", id);
        bookClassificationRepository.deleteById(id);
    }
}
