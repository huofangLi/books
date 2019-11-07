package com.books.server.repository;

import com.books.server.domain.BookClassification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BookClassification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookClassificationRepository extends JpaRepository<BookClassification, Long> {

}
