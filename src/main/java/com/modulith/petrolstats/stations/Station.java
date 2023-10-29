package com.modulith.petrolstats.stations;

public record Station(
        String id,
        String cityId,
        String provinceId,
        String communityId,
        StationPriceInfo priceInfo
) {
}
