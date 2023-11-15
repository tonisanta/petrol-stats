package com.modulith.petrolstats.stations.internal.infrastructure.writer;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.internal.domain.StationsWriterRepository;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

/**
 * StationsWriterRepository implementation using JPA
 */
@Repository
public class StationsWriterRepositoryImpl implements StationsWriterRepository {
    private final PricesByProvinceJpaRepository pricesByProvinceJpaRepository;
    private final PricesByCommunityJpaRepository pricesByCommunityJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(StationsWriterRepositoryImpl.class);

    public StationsWriterRepositoryImpl(PricesByProvinceJpaRepository pricesByProvinceJpaRepository,
                                        PricesByCommunityJpaRepository pricesByCommunityJpaRepository) {
        this.pricesByProvinceJpaRepository = pricesByProvinceJpaRepository;
        this.pricesByCommunityJpaRepository = pricesByCommunityJpaRepository;
    }

    @Override
    public void storePricesByGeo(GeoCategory geoCategory, Map<String, StationPriceInfo> pricesByGeo) {
        switch (geoCategory) {
            case PROVINCE -> {
                ArrayList<PriceByProvince> prices = new ArrayList<>();
                pricesByGeo.forEach((provinceId, priceInfo) -> {
                    var priceByProvince = new PriceByProvince(provinceId, priceInfo.petrol95(),
                            priceInfo.petrol98(), priceInfo.diesel(), priceInfo.dieselPremium());
                    prices.add(priceByProvince);
                });
                pricesByProvinceJpaRepository.saveAll(prices);
            }
            case AUTONOMOUS_COMMUNITY -> {
                ArrayList<PriceByCommunity> prices = new ArrayList<>();
                pricesByGeo.forEach((communityId, priceInfo) -> {
                    var priceByCommunity = new PriceByCommunity(communityId, priceInfo.petrol95(),
                            priceInfo.petrol98(), priceInfo.diesel(), priceInfo.dieselPremium());
                    prices.add(priceByCommunity);
                });
                pricesByCommunityJpaRepository.saveAll(prices);
            }
            default -> throw new IllegalStateException("Unexpected value: " + geoCategory);
        }

        logger.info("data has been stored properly, GeoCategory: " + geoCategory);
    }
}
