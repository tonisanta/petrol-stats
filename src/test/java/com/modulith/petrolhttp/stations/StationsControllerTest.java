package com.modulith.petrolhttp.stations;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.pricesbygeo.ComputePricesByGeo;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import com.modulith.petrolstats.stations.searchbyfilter.Filter;
import com.modulith.petrolstats.stations.searchbyfilter.GeoFilter;
import com.modulith.petrolstats.stations.searchbyfilter.SearchByFilter;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StationsControllerTest {
    private final Station[] stationsToReturn = new Station[]{
            new Station("1", "cityA", "provinceA", "communityA",
                    new StationPriceInfo(1.0, 2.5, 3.5, 4.0))
    };

    @Test
    void shouldReturn200AndAllStations() {
        SearchByFilter searchByFilter = mock();
        var stationsController = new StationsController(searchByFilter, null);
        when(searchByFilter.searchByFilter(null)).thenReturn(stationsToReturn);
        ResponseEntity<Station[]> response = stationsController.getStations(null, null);
        assertEquals(stationsToReturn, response.getBody());
    }

    @Test
    void shouldCreateAFilterBasedOnTheParams() {
        SearchByFilter searchByFilter = mock();
        var stationsController = new StationsController(searchByFilter, null);
        var expectedFilter = new Filter(new GeoFilter(GeoCategory.CITY, Set.of("cityA", "cityB")));
        when(searchByFilter.searchByFilter(expectedFilter)).thenReturn(stationsToReturn);
        ResponseEntity<Station[]> response = stationsController.getStations(GeoCategory.CITY, Set.of("cityA", "cityB"));
        assertEquals(stationsToReturn, response.getBody());
    }

    @Test
    void getPricesAggregatedByGeo() {
        ComputePricesByGeo computePricesByGeo = mock();
        var stationsController = new StationsController(null, computePricesByGeo);

        Map<String, StationPriceInfo> expectedPricesByCity = Map.of(
                "cityA", new StationPriceInfo(3.0, 3.0, 4.5, 5.0),
                "cityB", new StationPriceInfo(3.0, 2.0, 3.0, 4.0)
        );

        when(computePricesByGeo.computePricesByGeo(GeoCategory.PROVINCE)).thenReturn(expectedPricesByCity);
        ResponseEntity<Map<String, StationPriceInfo>> pricesAggregatedByGeo = stationsController.getPricesAggregatedByGeo(GeoCategory.PROVINCE);
        assertEquals(expectedPricesByCity, pricesAggregatedByGeo.getBody());
    }

}