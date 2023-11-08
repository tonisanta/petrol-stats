package com.modulith.petrolstats.stations.searchbyfilter;

import org.jetbrains.annotations.Nullable;

public interface SearchByFilter {
    Station[] searchByFilter(@Nullable Filter filter);
}
