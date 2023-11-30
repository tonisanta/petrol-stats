package com.modulith.petrolstats.stations.searchcheapestbyfilter;

import com.modulith.petrolstats.stations.Product;
import com.modulith.petrolstats.stations.searchbyfilter.Filter;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import org.jetbrains.annotations.Nullable;

public interface SearchCheapestByFilter {

    Station[] searchCheapestByFilterAndProduct(@Nullable Filter filter, Product product, int numStations);

}
