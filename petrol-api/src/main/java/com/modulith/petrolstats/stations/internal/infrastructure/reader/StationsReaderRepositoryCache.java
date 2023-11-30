package com.modulith.petrolstats.stations.internal.infrastructure.reader;

import com.modulith.petrolstats.stations.CacheUpdated;
import com.modulith.petrolstats.stations.DataNotAvailableException;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationsReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
class StationsReaderRepositoryCache implements StationsReaderRepository {
    private final Logger logger = LoggerFactory.getLogger(StationsReaderRepositoryCache.class);
    private final StationsReaderRepository stationsReaderRepository;
    private StationInternal[] data;
    private final ApplicationEventPublisher events;

    public StationsReaderRepositoryCache(@Qualifier("stationsReaderRepositoryImpl") StationsReaderRepository stationsReaderRepository,
                                         ApplicationEventPublisher events) {
        this.stationsReaderRepository = stationsReaderRepository;
        this.events = events;
    }

    @Override
    public StationInternal[] getStations() {
        if (data == null) {
            throw new DataNotAvailableException();
        }
        return data;
    }

    // Run every 30 minutes, is the refresh rate mentioned in the API docs
    @Scheduled(fixedRateString = "PT30M")
    void updateCache() {
        logger.info("getting new data");
        data = stationsReaderRepository.getStations();
        // TODO: add listener to create annotation in Grafana when new data is available
        events.publishEvent(new CacheUpdated());
        logger.info("cache data updated");
    }
}
