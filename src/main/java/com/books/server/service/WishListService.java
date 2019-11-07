package com.books.server.service;

import com.books.server.domain.WishList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link WishList}.
 */
public interface WishListService {

    /**
     * Save a wishList.
     *
     * @param wishList the entity to save.
     * @return the persisted entity.
     */
    WishList save(WishList wishList);

    /**
     * Get all the wishLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WishList> findAll(Pageable pageable);


    /**
     * Get the "id" wishList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WishList> findOne(Long id);

    /**
     * Delete the "id" wishList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
