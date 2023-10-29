package com.modulith.petrolstats.geography.internal.infrastructure;

import org.springframework.web.service.annotation.GetExchange;

interface GeoRepositorySpringClient {
    @GetExchange("/Municipios/")
    CityResponse[] GetCities();

    @GetExchange("/Provincias/")
    ProvinceResponse[] GetProvinces();

    @GetExchange("/ComunidadesAutonomas/")
    CommunityResponse[] GetCommunities();
}

