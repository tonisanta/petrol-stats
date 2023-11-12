package com.modulith.petrolstats.notifications;

import com.modulith.petrolstats.stations.CacheUpdated;

public interface CacheUpdatedListener {
    void notify(CacheUpdated cacheUpdated);
}
