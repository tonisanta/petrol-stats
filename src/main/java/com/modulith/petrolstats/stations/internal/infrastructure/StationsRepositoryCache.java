package com.modulith.petrolstats.stations.internal.infrastructure;

import com.modulith.petrolstats.stations.DataNotAvailableException;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
class StationsRepositoryCache implements StationsRepository {
    private final Logger logger = LoggerFactory.getLogger(StationsRepositoryCache.class);
    private final StationsRepository stationsRepository;
    private StationInternal[] data;

    public StationsRepositoryCache(@Qualifier("stationsRepositoryImpl") StationsRepository stationsRepository) {
        this.stationsRepository = stationsRepository;
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
        logger.info("cache data updated");
    }
}
