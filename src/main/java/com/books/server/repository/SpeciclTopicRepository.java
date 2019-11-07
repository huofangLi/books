package com.books.server.repository;

import com.books.server.domain.SpeciclTopic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SpeciclTopic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeciclTopicRepository extends JpaRepository<SpeciclTopic, Long> {

}
