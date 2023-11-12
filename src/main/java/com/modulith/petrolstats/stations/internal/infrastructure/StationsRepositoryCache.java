package com.modulith.petrolstats.stations.internal.infrastructure;

import com.modulith.petrolstats.stations.CacheUpdated;
import com.modulith.petrolstats.stations.DataNotAvailableException;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
class StationsRepositoryCache implements StationsRepository {
    private final Logger logger = LoggerFactory.getLogger(StationsRepositoryCache.class);
    private final StationsRepository stationsRepository;
    private StationInternal[] data;
    private final ApplicationEventPublisher events;

    public StationsRepositoryCache(@Qualifier("stationsRepositoryImpl") StationsRepository stationsRepository,
                                   ApplicationEventPublisher events) {
        this.stationsRepository = stationsRepository;
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
    void updateCache() throws DataNotAvailableException {
        logger.info("getting new data");
        data = stationsRepository.getStations();
        events.publishEvent(new CacheUpdated(data.length));
        logger.info("cache data updated");
    }
}
