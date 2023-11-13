package com.modulith.petrolstats.stations.internal.infrastructure.writer;

import org.springframework.data.repository.CrudRepository;

public interface PricesByCommunityJpaRepository extends CrudRepository<PriceByCommunity, Long> {
}
