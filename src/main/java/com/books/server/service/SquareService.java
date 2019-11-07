package com.books.server.service;

import com.books.server.domain.Square;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Square}.
 */
public interface SquareService {

    /**
     * Save a square.
     *
     * @param square the entity to save.
     * @return the persisted entity.
     */
    Square save(Square square);

    /**
     * Get all the squares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Square> findAll(Pageable pageable);


    /**
     * Get the "id" square.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Square> findOne(Long id);

    /**
     * Delete the "id" square.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
