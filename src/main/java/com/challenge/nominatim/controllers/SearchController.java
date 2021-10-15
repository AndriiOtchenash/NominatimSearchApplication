package com.challenge.nominatim.controllers;

import com.challenge.nominatim.entities.Address;
import com.challenge.nominatim.entities.SearchEntity;
import com.challenge.nominatim.services.NominatimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final NominatimService searchService;

    @Autowired
    public SearchController(NominatimService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public List<SearchEntity> search(@RequestParam String query) {
        return searchService.searchByAddress(query);
    }

    @GetMapping("/addresses")
    public List<Address> getAddresses() {
        return searchService.getAddresses();
    }
}
