package com.modulith.petrolstats.stations.internal.infrastructure.writer;

import org.springframework.data.repository.CrudRepository;

public interface PricesByProvinceJpaRepository extends CrudRepository<PriceByProvince, Long> {
}
