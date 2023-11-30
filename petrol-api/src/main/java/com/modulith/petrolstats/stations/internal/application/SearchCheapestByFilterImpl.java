package com.modulith.petrolstats.stations.internal.application;

import com.modulith.petrolstats.stations.Product;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.searchbyfilter.Filter;
import com.modulith.petrolstats.stations.searchbyfilter.SearchByFilter;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import com.modulith.petrolstats.stations.searchcheapestbyfilter.SearchCheapestByFilter;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

@Service
public class SearchCheapestByFilterImpl implements SearchCheapestByFilter {
    private final SearchByFilter searchByFilter;

    public SearchCheapestByFilterImpl(SearchByFilter searchByFilter) {
        this.searchByFilter = searchByFilter;
    }

    @Override
    public Station[] searchCheapestByFilterAndProduct(@Nullable Filter filter, Product product, int numStations) {
        var filteredStations = searchByFilter.searchByFilter(filter);

        var topStations = Arrays.stream(filteredStations).map(StationInternal::buildFromApiModel)
                .filter(stationInternal -> stationInternal.stationPrices().getByProduct(product) != null)
                .sorted(Comparator.comparingDouble(s -> s.stationPrices().getByProduct(product)))
                .limit(numStations);

        return mapToApiModel(topStations);
    }

    private Station[] mapToApiModel(Stream<StationInternal> stations) {
        return stations
                .map(StationInternal::toApiModel)
                .toArray(Station[]::new);
    }
}
