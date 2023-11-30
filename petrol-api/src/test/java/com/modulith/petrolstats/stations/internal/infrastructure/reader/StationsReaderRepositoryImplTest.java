package com.modulith.petrolstats.stations.internal.infrastructure.reader;

import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationPrices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class StationsReaderRepositoryImplTest {

    @Test
    void getStations() {
        RootResponse response = new RootResponse();
        RootResponse.StationDetails stationDetails = new RootResponse.StationDetails();

        // response retuns prices as strings and with comma, in our internal model we use Double
        stationDetails.id = "1234";
        stationDetails.petrol95 = "1,76";
        stationDetails.petrol98 = ""; // no data available, should return a null
        stationDetails.diesel = "1,35";
        stationDetails.dieselPremium = "1,23";
        stationDetails.cityId = "cityA";
        stationDetails.communityId = "communityA";
        stationDetails.provinceId = "provinceA";

        response.stations = new RootResponse.StationDetails[]{
                stationDetails
        };

        StationsRepositorySpringClient repositorySpringClient = mock();
        when(repositorySpringClient.getStations()).thenReturn(response);

        StationsReaderRepositoryImpl stationsRepository = new StationsReaderRepositoryImpl(repositorySpringClient);
        var stations = stationsRepository.getStations();
        var expectedStations = new StationInternal[]{
                new StationInternal("1234", "cityA", "provinceA", "communityA",
                        new StationPrices(1.76, null, 1.35, 1.23))
        };

        assertArrayEquals(expectedStations, stations);
    }
}