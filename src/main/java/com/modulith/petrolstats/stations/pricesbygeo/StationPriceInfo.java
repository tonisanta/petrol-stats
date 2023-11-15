package com.modulith.petrolstats.stations.pricesbygeo;

import org.jetbrains.annotations.Nullable;

public record StationPriceInfo(
        @Nullable Double petrol95,
        @Nullable Double petrol98,
        @Nullable Double diesel,
        @Nullable Double dieselPremium
) {
}
