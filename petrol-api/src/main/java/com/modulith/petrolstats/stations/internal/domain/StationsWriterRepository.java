package com.modulith.petrolstats.stations.internal.domain;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;

import java.util.Map;

public interface StationsWriterRepository {

    void storePricesByGeo(GeoCategory geoCategory, Map<String, StationPriceInfo> pricesByGeo);
}
