package com.modulith.petrolstats.stations.internal.application;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.Product;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import com.modulith.petrolstats.stations.searchbyfilter.Filter;
import com.modulith.petrolstats.stations.searchbyfilter.GeoFilter;
import com.modulith.petrolstats.stations.searchbyfilter.SearchByFilter;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchCheapestByFilterImplTest {
    private final Station[] stations = {
            new Station("1", "cityA", "provinceA", "communityD",
                    new StationPriceInfo(3.0, 2.0, null, 4.0)),
            new Station("2", "cityA", "provinceA", "communityD",
                    new StationPriceInfo(2.0, 2.0, 3.0, 4.0)),
            new Station("3", "cityA", "provinceA", "communityD",
                    new StationPriceInfo(2.48, 2.0, 3.2, 4.0))
    };

    @MethodSource
    @ParameterizedTest(name = "{index}: {0}")
    void shouldReturnTheTopNCheapestStations(@Nullable Filter filter, Product product, int numStations, Station[] expectedStations) {
        SearchByFilter searchByFilter = mock();
        SearchCheapestByFilterImpl searchCheapestByFilter = new SearchCheapestByFilterImpl(searchByFilter);
        when(searchByFilter.searchByFilter(filter)).thenReturn(stations);

        Station[] topStations = searchCheapestByFilter.searchCheapestByFilterAndProduct(filter, product, numStations);
        assertArrayEquals(expectedStations, topStations);
    }

    static Stream<Arguments> shouldReturnTheTopNCheapestStations() {
        var filterByCity = new Filter(new GeoFilter(GeoCategory.CITY, Set.of("cityA")));

        var expectedTop3Petrol95 = new Station[]{
                new Station("2", "cityA", "provinceA", "communityD",
                        new StationPriceInfo(2.0, 2.0, 3.0, 4.0)),
                new Station("3", "cityA", "provinceA", "communityD",
                        new StationPriceInfo(2.48, 2.0, 3.2, 4.0)),
                new Station("1", "cityA", "provinceA", "communityD",
                        new StationPriceInfo(3.0, 2.0, null, 4.0))
        };
        var expectedTop1Diesel = new Station[]{
                new Station("2", "cityA", "provinceA", "communityD",
                        new StationPriceInfo(2.0, 2.0, 3.0, 4.0))
        };

        return Stream.of(
                Arguments.of(Named.of("get top 3 cheapest petrol95 by city", filterByCity),
                        Product.PETROL95, 3, expectedTop3Petrol95),
                Arguments.of(Named.of("get top 6 cheapest petrol95 but city only has 3", filterByCity),
                        Product.PETROL95, 6, expectedTop3Petrol95),
                Arguments.of(Named.of("get top 1 cheapest diesel by city", filterByCity),
                        Product.DIESEL, 1, expectedTop1Diesel)
        );
    }

}
