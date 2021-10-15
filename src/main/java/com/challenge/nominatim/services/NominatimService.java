package com.challenge.nominatim.services;

import com.challenge.nominatim.entities.Address;
import com.challenge.nominatim.entities.Reverse;
import com.challenge.nominatim.entities.SearchEntity;
import com.challenge.nominatim.entities.SearchResult;
import com.challenge.nominatim.repositiries.SearchEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class NominatimService {
    private final SearchEntityRepository entityRepository;
    private final WebClient client = WebClient.builder()
            .baseUrl("https://nominatim.openstreetmap.org").build();

    @Autowired
    public NominatimService(SearchEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Cacheable("coordinates")
    public List<SearchEntity> searchByAddress(String query) {
        List<SearchEntity> resultsFromDb = entityRepository.findByQuery(query);
        if (!resultsFromDb.isEmpty()) {
            log.info("Response is queried from H2 DB");
            return resultsFromDb;
        }

        Mono<List<SearchResult>> response = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", query)
                        .queryParam("format", "json")
                        .build())
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<SearchResult>>() {
                });
        List<SearchResult> results = response.block();

        List<SearchEntity> searchEntities = new ArrayList<>();
        if (results == null) {
            return searchEntities;
        }
        for (SearchResult result : results) {
            SearchEntity searchEntity = new SearchEntity();
            searchEntity.setLatitude(result.getLat());
            searchEntity.setLongitude(result.getLon());
            searchEntity.setQuery(query);
            searchEntities.add(searchEntity);
            entityRepository.save(searchEntity);
        }
        log.info("Response is fetched from third party API");
        return searchEntities;
    }

    public List<Address> getAddresses() {
        List<SearchEntity> entities = entityRepository.findAll();
        List<Address> response = new ArrayList<>();

        for (SearchEntity entity : entities) {
            Reverse result = client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/reverse")
                            .queryParam("lat", entity.getLatitude())
                            .queryParam("lon", entity.getLongitude())
                            .queryParam("format", "json")
                            .build())
                    .retrieve().bodyToMono(Reverse.class).block();
            if (result != null) {
                response.add(result.getAddress());
            }
        }
        return response;
    }
}
