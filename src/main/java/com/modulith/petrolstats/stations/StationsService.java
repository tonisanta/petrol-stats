package com.modulith.petrolstats.stations;

import com.modulith.petrolstats.geography.GeoCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

// TODO: a service per each use case, this one could be SearchStationsService, another one for cheapestStation
// but then the consumer needs to inject multiple of them
public interface StationsService {
    Station[] getByFilter(@Nullable Filter filter);

    Map<String, StationPriceInfo> getPricesAggregatedByGeo(@NotNull GeoCategory geoCategory);
}
