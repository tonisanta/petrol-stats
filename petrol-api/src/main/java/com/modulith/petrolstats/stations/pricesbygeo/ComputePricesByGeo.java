package com.modulith.petrolstats.stations.pricesbygeo;

import com.modulith.petrolstats.geography.GeoCategory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ComputePricesByGeo {
    Map<String, StationPriceInfo> computePricesByGeo(@NotNull GeoCategory geoCategory);
}
