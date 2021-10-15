package com.challenge.nominatim.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    private String house_number;
    private String village;
    private String town;
    private String city;
    private String county;
    private String postcode;
    private String country;
    private String country_code;
    private String road;

    private String state_district;
    private String state;

    private String suburb;

    private String housenumber;
    private String street;
}
