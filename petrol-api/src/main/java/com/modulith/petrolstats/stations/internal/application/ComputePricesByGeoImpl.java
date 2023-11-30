package com.modulith.petrolstats.stations.internal.application;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationPrices;
import com.modulith.petrolstats.stations.internal.domain.StationsReaderRepository;
import com.modulith.petrolstats.stations.pricesbygeo.ComputePricesByGeo;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ComputePricesByGeoImpl implements ComputePricesByGeo {
    private final StationsReaderRepository stationsReaderRepository;

    ComputePricesByGeoImpl(@Qualifier("stationsReaderRepositoryCache") StationsReaderRepository stationsReaderRepository) {
        this.stationsReaderRepository = stationsReaderRepository;
    }

    @Override
    public Map<String, StationPriceInfo> computePricesByGeo(@NotNull GeoCategory geoCategory) {
        StationInternal[] stations = stationsReaderRepository.getStations();
        Function<StationInternal, String> geoIdPicker = StationInternal.getGeoIdPicker(geoCategory);

        Map<String, Double> avgPetrol95ByGeo = getAverageByGeoAndProduct(Arrays.stream(stations),
                geoIdPicker,
                s -> s.stationPrices().petrol95);

        Map<String, Double> avgPetrol98ByGeo = getAverageByGeoAndProduct(Arrays.stream(stations),
                geoIdPicker,
                s -> s.stationPrices().petrol98);

        Map<String, Double> avgDieselByGeo = getAverageByGeoAndProduct(Arrays.stream(stations),
                geoIdPicker,
                s -> s.stationPrices().diesel);

        Map<String, Double> avgDieselPremiumByGeo = getAverageByGeoAndProduct(Arrays.stream(stations),
                geoIdPicker,
                s -> s.stationPrices().dieselPremium);


        // to simply the initialization of the values, we are going to get all the ids to instantiate
        // the price object
        Set<String> geoIds = Stream.of(
                        avgPetrol95ByGeo.keySet(),
                        avgPetrol98ByGeo.keySet(),
                        avgDieselByGeo.keySet(),
                        avgDieselPremiumByGeo.keySet())
                .flatMap(Set::stream)
                .collect(Collectors.toSet());


        var pricesByGeoId = new HashMap<String, StationPrices>(geoIds.size());
        for (String id : geoIds) {
            pricesByGeoId.put(id, new StationPrices());
        }

        avgPetrol95ByGeo.forEach((geoId, average) -> pricesByGeoId.get(geoId).petrol95 = average);
        avgPetrol98ByGeo.forEach((geoId, average) -> pricesByGeoId.get(geoId).petrol98 = average);
        avgDieselByGeo.forEach((geoId, average) -> pricesByGeoId.get(geoId).diesel = average);
        avgDieselPremiumByGeo.forEach((geoId, average) -> pricesByGeoId.get(geoId).dieselPremium = average);

        // map to the api record
        var priceInfoByGeo = new HashMap<String, StationPriceInfo>(geoIds.size());
        pricesByGeoId.forEach((id, prices) -> {
            priceInfoByGeo.put(id, new StationPriceInfo(prices.petrol95, prices.petrol98, prices.diesel, prices.dieselPremium));
        });
        return priceInfoByGeo;
    }


    private static Map<String, Double> getAverageByGeoAndProduct(Stream<StationInternal> filteredStations,
                                                                 Function<StationInternal, String> geoPickerFunction,
                                                                 Function<StationInternal, Double> getProduct) {
        return filteredStations
                .filter(station -> getProduct.apply(station) != null)
                .collect(Collectors.groupingBy(
                        geoPickerFunction,
                        Collectors.averagingDouble(getProduct::apply)
                ));
    }
}
