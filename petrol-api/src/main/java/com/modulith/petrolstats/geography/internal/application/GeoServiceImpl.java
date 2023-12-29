package com.modulith.petrolstats.geography.internal.application;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.geography.GeoResponse;
import com.modulith.petrolstats.geography.GeoService;
import com.modulith.petrolstats.geography.internal.domain.GeoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;


@Service
class GeoServiceImpl implements GeoService {
    private final GeoRepository geoRepository;

    public GeoServiceImpl(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    public GeoResponse[] getAll(@NotNull GeoCategory category) {
        return geoRepository.getGeoInfoByCategory(category);
    }
}
