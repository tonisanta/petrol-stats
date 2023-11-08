package com.modulith.petrolstats.stations.spi;

import com.modulith.petrolstats.stations.Filter;
import com.modulith.petrolstats.stations.Station;
import org.jetbrains.annotations.Nullable;

public interface SearchByFilter {
    Station[] searchByFilter(@Nullable Filter filter);
}
