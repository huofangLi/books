package com.books.server.service.impl;

import com.books.server.service.SquareService;
import com.books.server.domain.Square;
import com.books.server.repository.SquareRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Square}.
 */
@Service
@Transactional
public class SquareServiceImpl implements SquareService {

    private final Logger log = LoggerFactory.getLogger(SquareServiceImpl.class);

    private final SquareRepository squareRepository;

    public SquareServiceImpl(SquareRepository squareRepository) {
        this.squareRepository = squareRepository;
    }

    /**
     * Save a square.
     *
     * @param square the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Square save(Square square) {
        log.debug("Request to save Square : {}", square);
        return squareRepository.save(square);
    }

    /**
     * Get all the squares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Square> findAll(Pageable pageable) {
        log.debug("Request to get all Squares");
        return squareRepository.findAll(pageable);
    }


    /**
     * Get one square by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Square> findOne(Long id) {
        log.debug("Request to get Square : {}", id);
        return squareRepository.findById(id);
    }

    /**
     * Delete the square by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Square : {}", id);
        squareRepository.deleteById(id);
    }
}
