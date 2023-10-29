package com.modulith.petrolhttp.stations;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StationsControllerTest {
    private final Station[] stationsToReturn;
    private final StationsService stationsService;
    private final StationsController stationsController;

    StationsControllerTest() {
        stationsService = mock();
        stationsController = new StationsController(stationsService);
        stationsToReturn = new Station[]{
                new Station("1", "cityA", "provinceA", "communityA",
                        new StationPriceInfo(1.0, 2.5, 3.5, 4.0))
        };
    }

    @Test
    void shouldReturn200AndAllStations() {
        when(stationsService.getByFilter(null)).thenReturn(stationsToReturn);
        ResponseEntity<Station[]> response = stationsController.getStations(null, null);
        assertEquals(stationsToReturn, response.getBody());
    }

    @Test
    void shouldCreateAFilterBasedOnTheParams() {
        var expectedFilter = new Filter(new GeoFilter(GeoCategory.CITY, Set.of("cityA", "cityB")));
        when(stationsService.getByFilter(expectedFilter)).thenReturn(stationsToReturn);
        ResponseEntity<Station[]> response = stationsController.getStations(GeoCategory.CITY, Set.of("cityA", "cityB"));
        assertEquals(stationsToReturn, response.getBody());
    }

    @Test
    void getPricesAggregatedByGeo() {
        Map<String, StationPriceInfo> expectedPricesByCity = Map.of(
                "cityA", new StationPriceInfo(3.0, 3.0, 4.5, 5.0),
                "cityB", new StationPriceInfo(3.0, 2.0, 3.0, 4.0)
        );

        when(stationsService.getPricesAggregatedByGeo(GeoCategory.PROVINCE)).thenReturn(expectedPricesByCity);
        Map<String, StationPriceInfo> pricesAggregatedByGeo = stationsController.getPricesAggregatedByGeo(GeoCategory.PROVINCE);
        assertEquals(expectedPricesByCity, pricesAggregatedByGeo);
    }

}