package com.modulith.petrolstats.stations.internal.domain;

import com.modulith.petrolstats.stations.Product;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StationPrices {
    @Nullable
    public Double petrol95;
    @Nullable
    public Double petrol98;
    @Nullable
    public Double diesel;
    @Nullable
    public Double dieselPremium;

    public StationPrices() {
    }

    public StationPrices(@Nullable Double petrol95, @Nullable Double petrol98, @Nullable Double diesel, @Nullable Double dieselPremium) {
        this.petrol95 = petrol95;
        this.petrol98 = petrol98;
        this.diesel = diesel;
        this.dieselPremium = dieselPremium;
    }

    public StationPriceInfo toApiModel() {
        return new StationPriceInfo(petrol95, petrol98, diesel, dieselPremium);
    }

    public static StationPrices buildFromApiModel(StationPriceInfo stationPriceInfo) {
        return new StationPrices(stationPriceInfo.petrol95(), stationPriceInfo.petrol98(),
                stationPriceInfo.diesel(), stationPriceInfo.dieselPremium());
    }

    public Double getByProduct(Product product) {
        return switch (product) {
            case PETROL95 -> this.petrol95;
            case PETROL98 -> this.petrol98;
            case DIESEL -> this.diesel;
            case DIESEL_PREMIUM -> this.dieselPremium;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationPrices that = (StationPrices) o;

        if (!Objects.equals(petrol95, that.petrol95)) return false;
        if (!Objects.equals(petrol98, that.petrol98)) return false;
        if (!Objects.equals(diesel, that.diesel)) return false;
        return Objects.equals(dieselPremium, that.dieselPremium);
    }

    @Override
    public int hashCode() {
        int result = petrol95 != null ? petrol95.hashCode() : 0;
        result = 31 * result + (petrol98 != null ? petrol98.hashCode() : 0);
        result = 31 * result + (diesel != null ? diesel.hashCode() : 0);
        result = 31 * result + (dieselPremium != null ? dieselPremium.hashCode() : 0);
        return result;
    }
}
