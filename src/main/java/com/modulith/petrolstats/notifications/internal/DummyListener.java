package com.modulith.petrolstats.notifications.internal;

import com.modulith.petrolstats.notifications.CacheUpdatedListener;
import com.modulith.petrolstats.stations.CacheUpdated;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DummyListener implements CacheUpdatedListener {
    @Override
    public void notify(CacheUpdated cacheUpdated) {
        System.out.println("Processing new data ...");
        try {
            Thread.sleep(Duration.of(200, ChronoUnit.MILLIS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Processing new data - done");
    }
}
