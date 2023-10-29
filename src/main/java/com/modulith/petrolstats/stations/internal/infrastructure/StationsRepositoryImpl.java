package com.modulith.petrolstats.stations.internal.infrastructure;

import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationPrices;
import com.modulith.petrolstats.stations.internal.domain.StationsRepository;
import org.springframework.stereotype.Repository;

@Repository
class StationsRepositoryImpl implements StationsRepository {
    private final StationsRepositorySpringClient repositorySpringClient;

    public StationsRepositoryImpl(StationsRepositorySpringClient repositorySpringClient) {
        this.repositorySpringClient = repositorySpringClient;
    }

    @Override
    public StationInternal[] getStations() {
        RootResponse rootResponse = repositorySpringClient.getStations();

        StationInternal[] stations = new StationInternal[rootResponse.stations.length];
        RootResponse.StationDetails[] stationDetails = rootResponse.stations;
        for (int i = 0; i < stationDetails.length; i++) {
            RootResponse.StationDetails s = stationDetails[i];

            Double petrol95 = s.petrol95.isBlank() ? null : Double.parseDouble(s.petrol95.replace(",","."));
            Double petrol98 = s.petrol98.isBlank() ? null : Double.parseDouble(s.petrol98.replace(",","."));
            Double diesel = s.diesel.isBlank() ? null : Double.parseDouble(s.diesel.replace(",","."));
            Double dieselPremium = s.dieselPremium.isBlank() ? null : Double.parseDouble(s.dieselPremium.replace(",","."));

            var stationPrices = new StationPrices(petrol95, petrol98, diesel, dieselPremium);
            var station = new StationInternal(s.id, s.cityId, s.provinceId, s.communityId, stationPrices);
            stations[i] = station;
        }

        return stations;
    }
}
