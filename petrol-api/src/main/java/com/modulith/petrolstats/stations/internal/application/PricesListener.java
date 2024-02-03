package com.modulith.petrolstats.stations.internal.application;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.reports.CacheUpdatedListener;
import com.modulith.petrolstats.stations.CacheUpdated;
import com.modulith.petrolstats.stations.internal.domain.StationsWriterRepository;
import com.modulith.petrolstats.stations.pricesbygeo.ComputePricesByGeo;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PricesListener {
    private final Logger logger = LoggerFactory.getLogger(PricesListener.class);
    private final ComputePricesByGeo computePricesByGeo;
    private final StationsWriterRepository stationsWriterRepository;

    public PricesListener(ComputePricesByGeo computePricesByGeo, StationsWriterRepository stationsWriterRepository) {
        this.computePricesByGeo = computePricesByGeo;
        this.stationsWriterRepository = stationsWriterRepository;
    }

    @EventListener
    public void onCacheUpdated(CacheUpdated event) {
        logger.info("Storing new data ...");
        Map<String, StationPriceInfo> pricesByProvince = computePricesByGeo.computePricesByGeo(GeoCategory.PROVINCE);
        Map<String, StationPriceInfo> pricesByCommunity = computePricesByGeo.computePricesByGeo(GeoCategory.AUTONOMOUS_COMMUNITY);
        stationsWriterRepository.storePricesByGeo(GeoCategory.PROVINCE, pricesByProvince);
        stationsWriterRepository.storePricesByGeo(GeoCategory.AUTONOMOUS_COMMUNITY, pricesByCommunity);
        logger.info("Storing new data - done");
    }

}
