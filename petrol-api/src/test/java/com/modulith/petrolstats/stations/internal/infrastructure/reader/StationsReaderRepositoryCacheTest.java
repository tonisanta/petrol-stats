package com.modulith.petrolstats.stations.internal.infrastructure.reader;

import com.modulith.petrolstats.stations.CacheUpdated;
import com.modulith.petrolstats.stations.DataNotAvailableException;
import com.modulith.petrolstats.stations.internal.domain.StationInternal;
import com.modulith.petrolstats.stations.internal.domain.StationPrices;
import com.modulith.petrolstats.stations.internal.domain.StationsReaderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StationsReaderRepositoryCacheTest {
    @Test
    void shouldThrowAnExceptionWhenDataIsNotAvailable() {
        StationsReaderRepository stationsReaderRepository = mock();
        ApplicationEventPublisher applicationEventPublisher = mock();
        StationsReaderRepositoryCache repositoryCache = new StationsReaderRepositoryCache(stationsReaderRepository, applicationEventPublisher);
        assertThrows(DataNotAvailableException.class, repositoryCache::getStations);
        verify(applicationEventPublisher, times(0)).publishEvent(Mockito.any(CacheUpdated.class));
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

        StationsReaderRepository stationsReaderRepository = mock();
        when(stationsReaderRepository.getStations()).thenReturn(stationsFirstVersion);
        ApplicationEventPublisher applicationEventPublisher = mock();
        StationsReaderRepositoryCache repositoryCache = new StationsReaderRepositoryCache(stationsReaderRepository, applicationEventPublisher);

        repositoryCache.updateCache();
        assertArrayEquals(stationsFirstVersion, repositoryCache.getStations());

        // simulating new data is available
        when(stationsReaderRepository.getStations()).thenReturn(stationsSecondVersion);
        repositoryCache.updateCache();
        assertArrayEquals(stationsSecondVersion, repositoryCache.getStations());

        verify(applicationEventPublisher, times(2)).publishEvent(Mockito.any(CacheUpdated.class));
    }
}