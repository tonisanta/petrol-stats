package com.modulith.petrolstats.stations.internal.application;

import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationsReaderRepository;
import com.modulith.petrolstats.stations.searchbyfilter.Filter;
import com.modulith.petrolstats.stations.searchbyfilter.SearchByFilter;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class SearchByFilterImpl implements SearchByFilter {

    private final StationsReaderRepository stationsReaderRepository;

    SearchByFilterImpl(@Qualifier("stationsReaderRepositoryCache") StationsReaderRepository stationsReaderRepository) {
        this.stationsReaderRepository = stationsReaderRepository;
    }

    public Station[] searchByFilter(@Nullable Filter filter) {
        StationInternal[] stations = stationsReaderRepository.getStations();
        if (filter == null) {
            return mapToApiModel(Arrays.stream(stations));
        }

        Stream<StationInternal> filteredStations = Arrays.stream(stations);
        if (filter.geoFilter() != null) {
            var geoFilter = filter.geoFilter();
            Function<StationInternal, String> geoIdPicker = StationInternal.getGeoIdPicker(geoFilter.geoCategory());
            filteredStations = filteredStations.filter(station -> geoFilter.ids().contains(geoIdPicker.apply(station)));
        }

        return mapToApiModel(filteredStations);
    }

    private Station[] mapToApiModel(Stream<StationInternal> stations) {
        return stations
                .map(s -> new Station(s.id(), s.cityId(), s.provinceId(), s.communityId(), s.stationPrices().ToStationPriceInfo()))
                .toArray(Station[]::new);
    }

}
