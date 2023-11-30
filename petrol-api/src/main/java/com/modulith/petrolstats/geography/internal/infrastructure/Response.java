package com.modulith.petrolstats.geography.internal.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class CityResponse {
    @JsonProperty("IDMunicipio")
    String id;
    @JsonProperty("Municipio")
    String name;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ProvinceResponse {
    @JsonProperty("IDPovincia")
    String id;
    @JsonProperty("Provincia")
    String name;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class CommunityResponse {
    @JsonProperty("IDCCAA")
    String id;
    @JsonProperty("CCAA")
    String name;
}