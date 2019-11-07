package com.books.server.repository;

import com.books.server.domain.Square;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Square entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SquareRepository extends JpaRepository<Square, Long> {

}
