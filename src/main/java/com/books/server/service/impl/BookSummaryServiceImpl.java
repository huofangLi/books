package com.books.server.service.impl;

import com.books.server.service.BookSummaryService;
import com.books.server.domain.BookSummary;
import com.books.server.repository.BookSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BookSummary}.
 */
@Service
@Transactional
public class BookSummaryServiceImpl implements BookSummaryService {

    private final Logger log = LoggerFactory.getLogger(BookSummaryServiceImpl.class);

    private final BookSummaryRepository bookSummaryRepository;

    public BookSummaryServiceImpl(BookSummaryRepository bookSummaryRepository) {
        this.bookSummaryRepository = bookSummaryRepository;
    }

    /**
     * Save a bookSummary.
     *
     * @param bookSummary the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BookSummary save(BookSummary bookSummary) {
        log.debug("Request to save BookSummary : {}", bookSummary);
        return bookSummaryRepository.save(bookSummary);
    }

    /**
     * Get all the bookSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BookSummary> findAll(Pageable pageable) {
        log.debug("Request to get all BookSummaries");
        return bookSummaryRepository.findAll(pageable);
    }


    /**
     * Get one bookSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BookSummary> findOne(Long id) {
        log.debug("Request to get BookSummary : {}", id);
        return bookSummaryRepository.findById(id);
    }

    /**
     * Delete the bookSummary by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BookSummary : {}", id);
        bookSummaryRepository.deleteById(id);
    }
}
