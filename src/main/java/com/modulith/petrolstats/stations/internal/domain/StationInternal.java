package com.modulith.petrolstats.stations.internal.domain;

// I should elaborate more on the naming, but it's to make it more explicit, this only used in the internal package
public record StationInternal(
        String id,
        String cityId,
        String provinceId,
        String communityId,
        StationPrices stationPrices
) {

}
