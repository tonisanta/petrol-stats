package com.modulith.petrolstats.stations.pricesbygeo;

import com.modulith.petrolstats.geography.GeoCategory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

// TODO: if a class it's only used in a particular use case, move it inside a package for that use case
public interface ComputePricesByGeo {
    Map<String, StationPriceInfo> computePricesByGeo(@NotNull GeoCategory geoCategory);
}
