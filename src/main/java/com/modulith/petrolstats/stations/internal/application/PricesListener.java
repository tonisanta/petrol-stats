package com.modulith.petrolstats.stations.internal.application;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.CacheUpdated;
import com.modulith.petrolstats.stations.internal.domain.StationsWriterRepository;
import com.modulith.petrolstats.stations.pricesbygeo.ComputePricesByGeo;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PricesListener {

    private final ComputePricesByGeo computePricesByGeo;
    private final StationsWriterRepository stationsWriterRepository;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public PricesListener(ComputePricesByGeo computePricesByGeo, StationsWriterRepository stationsWriterRepository) {
        this.computePricesByGeo = computePricesByGeo;
        this.stationsWriterRepository = stationsWriterRepository;
    }

    @EventListener
    public void onCacheUpdated(CacheUpdated event) {
        System.out.println("Storing new data ...");

        Map<String, StationPriceInfo> pricesByProvince = computePricesByGeo.computePricesByGeo(GeoCategory.PROVINCE);
        Map<String, StationPriceInfo> pricesByCommunity = computePricesByGeo.computePricesByGeo(GeoCategory.AUTONOMOUS_COMMUNITY);

        stationsWriterRepository.storePricesByGeo(GeoCategory.PROVINCE, pricesByProvince);
        stationsWriterRepository.storePricesByGeo(GeoCategory.AUTONOMOUS_COMMUNITY, pricesByCommunity);
    }

}
