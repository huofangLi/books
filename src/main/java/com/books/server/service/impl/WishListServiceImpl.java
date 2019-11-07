package com.books.server.service.impl;

import com.books.server.service.WishListService;
import com.books.server.domain.WishList;
import com.books.server.repository.WishListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WishList}.
 */
@Service
@Transactional
public class WishListServiceImpl implements WishListService {

    private final Logger log = LoggerFactory.getLogger(WishListServiceImpl.class);

    private final WishListRepository wishListRepository;

    public WishListServiceImpl(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    /**
     * Save a wishList.
     *
     * @param wishList the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WishList save(WishList wishList) {
        log.debug("Request to save WishList : {}", wishList);
        return wishListRepository.save(wishList);
    }

    /**
     * Get all the wishLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WishList> findAll(Pageable pageable) {
        log.debug("Request to get all WishLists");
        return wishListRepository.findAll(pageable);
    }


    /**
     * Get one wishList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WishList> findOne(Long id) {
        log.debug("Request to get WishList : {}", id);
        return wishListRepository.findById(id);
    }

    /**
     * Delete the wishList by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WishList : {}", id);
        wishListRepository.deleteById(id);
    }
}
