package com.books.server.repository;

import com.books.server.domain.BookSummary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BookSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookSummaryRepository extends JpaRepository<BookSummary, Long> {

}
