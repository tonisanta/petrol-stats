package com.modulith.petrolstats.listener;

import com.modulith.petrolstats.stations.CacheUpdated;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    @EventListener
    public void onCacheUpdated(CacheUpdated event) {
        System.out.println("cache has been updated!!");
    }
}
