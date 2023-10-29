package com.modulith.petrolstats.geography.internal.domain;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.geography.GeoResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface GeoRepository {
    GeoResponse[] getGeoInfoByCategory(@NotNull GeoCategory geoCategory);
}
