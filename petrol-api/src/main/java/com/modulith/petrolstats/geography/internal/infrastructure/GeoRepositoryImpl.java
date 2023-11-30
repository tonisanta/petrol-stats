package com.modulith.petrolstats.geography.internal.infrastructure;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.geography.GeoResponse;
import com.modulith.petrolstats.geography.internal.domain.GeoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
class GeoRepositoryImpl implements GeoRepository {
    private final GeoRepositorySpringClient geoRepositorySpringClient;

    public GeoRepositoryImpl(GeoRepositorySpringClient geoRepositorySpringClient) {
        this.geoRepositorySpringClient = geoRepositorySpringClient;
    }

    @Override
    public GeoResponse[] getGeoInfoByCategory(@NotNull GeoCategory geoCategory) {
        return switch (geoCategory) {
            case CITY -> getGeoInfoByCity();
            case PROVINCE -> getGeoInfoByProvince();
            case AUTONOMOUS_COMMUNITY -> getGeoInfoByCommunity();
        };
    }

    private GeoResponse[] getGeoInfoByCity() {
        CityResponse[] citiesResponse = geoRepositorySpringClient.GetCities();
        var cities = Arrays.stream(citiesResponse)
                .map(city -> new GeoResponse(city.id, city.name))
                .toArray(GeoResponse[]::new);

        return cities;
    }

    private GeoResponse[] getGeoInfoByProvince() {
        ProvinceResponse[] provincesResponse = geoRepositorySpringClient.GetProvinces();
        var provinces = Arrays.stream(provincesResponse)
                .map(province -> new GeoResponse(province.id, province.name))
                .toArray(GeoResponse[]::new);

        return provinces;
    }

    private GeoResponse[] getGeoInfoByCommunity() {
        CommunityResponse[] communitiesResponse = geoRepositorySpringClient.GetCommunities();
        var communities = Arrays.stream(communitiesResponse)
                .map(com -> new GeoResponse(com.id, com.name))
                .toArray(GeoResponse[]::new);

        return communities;
    }
}
