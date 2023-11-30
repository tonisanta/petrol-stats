package com.modulith.petrolstats.stations.searchbyfilter;

import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;

public record Station(
        String id,
        String cityId,
        String provinceId,
        String communityId,
        StationPriceInfo priceInfo
) {
}
