package com.modulith.petrolstats.stations;

import com.modulith.petrolstats.geography.GeoCategory;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record GeoFilter(@NotNull GeoCategory geoCategory, @NotNull Set<String> ids) {
}
