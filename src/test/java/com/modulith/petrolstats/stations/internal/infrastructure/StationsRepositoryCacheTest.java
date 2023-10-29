package com.modulith.petrolstats.stations.internal.infrastructure;

import com.modulith.petrolstats.stations.DataNotAvailableException;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationPrices;
import com.modulith.petrolstats.stations.internal.domain.StationsRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StationsRepositoryCacheTest {

    @Test
    void shouldThrowAnExceptionWhenDataIsNotAvailable() {
        StationsRepository stationsRepository = mock();
        StationsRepositoryCache repositoryCache = new StationsRepositoryCache(stationsRepository);
        assertThrows(DataNotAvailableException.class, repositoryCache::getStations);
    }

    @Test
    void shouldUpdateCacheWithNewData() {
        StationInternal[] stationsFirstVersion = {
                new StationInternal("1", "cityC", "provinceA", "communityD",
                        new StationPrices(1.0, 2.0, 6.4, 4.0))
        };

        StationInternal[] stationsSecondVersion = {
                new StationInternal("2", "cityA", "provinceD", "communityB",
                        new StationPrices(3.0, 2.0, 4.0, 4.0))
        };

        StationsRepository stationsRepository = mock();
        when(stationsRepository.getStations()).thenReturn(stationsFirstVersion);
        StationsRepositoryCache repositoryCache = new StationsRepositoryCache(stationsRepository);

        repositoryCache.updateCache();
        assertArrayEquals(stationsFirstVersion, repositoryCache.getStations());

        // simulating new data is available
        when(stationsRepository.getStations()).thenReturn(stationsSecondVersion);

        repositoryCache.updateCache();
        assertArrayEquals(stationsSecondVersion, repositoryCache.getStations());
    }
}