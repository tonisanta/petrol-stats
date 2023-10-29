package com.modulith.petrolstats.geography;

import org.jetbrains.annotations.NotNull;

public interface GeoService {
    GeoResponse[] getAll(@NotNull GeoCategory category);
}
