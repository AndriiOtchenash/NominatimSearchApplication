package com.challenge.nominatim.repositiries;

import com.challenge.nominatim.entities.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchEntityRepository extends JpaRepository<SearchEntity, Long> {

    List<SearchEntity> findByQuery(String query);
}
