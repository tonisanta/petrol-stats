package com.modulith.petrolstats.stations.spi;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.StationPriceInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ComputePricesByGeo {
    Map<String, StationPriceInfo> computePricesByGeo(@NotNull GeoCategory geoCategory);
}
