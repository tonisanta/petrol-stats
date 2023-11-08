package com.modulith.petrolstats.stations.internal.application;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.Filter;
import com.modulith.petrolstats.stations.Station;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationsRepository;
import com.modulith.petrolstats.stations.spi.SearchByFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class SearchByFilterImpl implements SearchByFilter {

    private final StationsRepository stationsRepository;

    SearchByFilterImpl(@Qualifier("stationsRepositoryCache") StationsRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
    }

    public Station[] searchByFilter(@Nullable Filter filter) {
        StationInternal[] stations = stationsRepository.getStations();
        if (filter == null) {
            return mapToApiModel(Arrays.stream(stations));
        }

        Stream<StationInternal> filteredStations = Arrays.stream(stations);
        if (filter.geoFilter() != null) {
            var geoFilter = filter.geoFilter();
            Function<StationInternal, String> geoIdPicker = getGeoIdPicker(geoFilter.geoCategory());
            filteredStations = filteredStations.filter(station -> geoFilter.ids().contains(geoIdPicker.apply(station)));
        }

        return mapToApiModel(filteredStations);
    }

    private Station[] mapToApiModel(Stream<StationInternal> stations) {
        return stations
                .map(s -> new Station(s.id(), s.cityId(), s.provinceId(), s.communityId(), s.stationPrices().ToStationPriceInfo()))
                .toArray(Station[]::new);
    }

    // TODO: maybe move to domain?
    private static Function<StationInternal, String> getGeoIdPicker(@NotNull GeoCategory geoCategory) {
        return switch (geoCategory) {
            case CITY -> StationInternal::cityId;
            case PROVINCE -> StationInternal::provinceId;
            case AUTONOMOUS_COMMUNITY -> StationInternal::communityId;
        };
    }
}
