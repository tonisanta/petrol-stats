package com.modulith.petrolstats.stations.internal.application;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationPrices;
import com.modulith.petrolstats.stations.internal.domain.StationsRepository;
import com.modulith.petrolstats.stations.searchbyfilter.Filter;
import com.modulith.petrolstats.stations.searchbyfilter.GeoFilter;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchByFilterImplTest {

    // TODO: have it in a common place maybe or use different data?
    private final StationInternal[] stations = {
            new StationInternal("1", "cityC", "provinceA", "communityD",
                    new StationPrices(1.0, 2.0, 6.4, 4.0)),

            new StationInternal("2", "cityA", "provinceC", "communityB",
                    new StationPrices(2.0, 4.5, 3.0, 6.0)),

            new StationInternal("3", "cityB", "provinceA", "communityB",
                    new StationPrices(3.0, 2.0, 3.0, 4.0)),

            new StationInternal("4", "cityA", "provinceA", "communityC",
                    new StationPrices(4.0, 1.5, 6.0, 4.0))
    };

    @MethodSource
    @ParameterizedTest(name = "{index}: {0}")
    void getByFilter(Filter filter, String[] expectedIds) {
        StationsRepository stationsRepository = mock();
        when(stationsRepository.getStations()).thenReturn(stations);

        SearchByFilterImpl stationsService = new SearchByFilterImpl(stationsRepository);
        Station[] filteredStations = stationsService.searchByFilter(filter);

        String[] ids = Arrays.stream(filteredStations).map(Station::id).toArray(String[]::new);
        assertArrayEquals(expectedIds, ids);
    }

    static Stream<Arguments> getByFilter() {
        var filterByCity = new Filter(new GeoFilter(GeoCategory.CITY, Set.of("cityA")));
        var filterByProvince = new Filter(new GeoFilter(GeoCategory.PROVINCE, Set.of("provinceA")));
        var filterByCommunity = new Filter(new GeoFilter(GeoCategory.AUTONOMOUS_COMMUNITY, Set.of("communityA")));

        return Stream.of(
                Arguments.of(Named.of("should return all of them when no filter is provided", null),
                        new String[]{"1", "2", "3", "4"}),

                Arguments.of(Named.of("should filter by city", filterByCity),
                        new String[]{"2", "4"}),

                Arguments.of(Named.of("should filter by province", filterByProvince),
                        new String[]{"1", "3", "4"}),

                Arguments.of(Named.of("should filter by community", filterByCommunity),
                        new String[]{})
        );
    }
}
