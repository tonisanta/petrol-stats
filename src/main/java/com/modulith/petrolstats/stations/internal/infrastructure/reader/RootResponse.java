package com.modulith.petrolstats.stations.internal.infrastructure.reader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class RootResponse {
    @JsonProperty("ListaEESSPrecio")
    StationDetails[] stations;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class StationDetails {
        @JsonProperty("IDEESS")
        String id;
        @JsonProperty("IDMunicipio")
        String cityId;
        @JsonProperty("IDProvincia")
        String provinceId;
        @JsonProperty("IDCCAA")
        String communityId;
        @JsonProperty("Precio Gasolina 95 E5")
        String petrol95;
        @JsonProperty("Precio Gasolina 98 E5")
        String petrol98;
        @JsonProperty("Precio Gasoleo A")
        String diesel;
        @JsonProperty("Precio Gasoleo Premium")
        String dieselPremium;
    }
}

