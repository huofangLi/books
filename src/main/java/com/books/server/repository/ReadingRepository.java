package com.books.server.repository;

import com.books.server.domain.Reading;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Reading entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {

}
