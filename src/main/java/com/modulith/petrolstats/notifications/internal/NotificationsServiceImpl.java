package com.modulith.petrolstats.notifications.internal;

import com.modulith.petrolstats.notifications.CacheUpdatedListener;
import com.modulith.petrolstats.notifications.NotificationsService;
import com.modulith.petrolstats.stations.CacheUpdated;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationsServiceImpl implements NotificationsService {
    private final Set<CacheUpdatedListener> listeners = new HashSet<>(List.of(new DummyListener()));

    @EventListener
    public void onCacheUpdated(CacheUpdated event) {
        System.out.println("New data available");
        for (CacheUpdatedListener listener : listeners) {
            listener.notify(event);
        }
    }

    @Override
    public void addListener(CacheUpdatedListener listener) {
        listeners.add(listener);
    }
}
