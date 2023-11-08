package com.modulith.petrolstats.listener;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.CacheUpdated;
import com.modulith.petrolstats.stations.searchbyfilter.GeoFilter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class Listener {

    @EventListener
    public void onCacheUpdated(CacheUpdated event) {
        System.out.println("cache has been updated!!");
        // INVALID - it's a reference to a class inside the internal
        // var stationInternal = new StationPrices();
        
        // VALID - as it's part of the public api, in the searchbyfilter package
        var filter = new GeoFilter(GeoCategory.CITY, Set.of("2"));
    }
}
