package com.modulith.petrolstats.stations.internal.domain;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// I should elaborate more on the naming, but it's to make it more explicit, this only used in the internal package
public record StationInternal(
        String id,
        String cityId,
        String provinceId,
        String communityId,
        StationPrices stationPrices
) {
    public static Function<StationInternal, String> getGeoIdPicker(@NotNull GeoCategory geoCategory) {
        return switch (geoCategory) {
            case CITY -> StationInternal::cityId;
            case PROVINCE -> StationInternal::provinceId;
            case AUTONOMOUS_COMMUNITY -> StationInternal::communityId;
        };
    }

    public Station toApiModel() {
        return new Station(id, cityId, provinceId, communityId, stationPrices.toApiModel());
    }

    public static StationInternal buildFromApiModel(Station station) {
        return new StationInternal(station.id(), station.cityId(), station.provinceId(),
                station.communityId(), StationPrices.buildFromApiModel(station.priceInfo()));
    }
}
