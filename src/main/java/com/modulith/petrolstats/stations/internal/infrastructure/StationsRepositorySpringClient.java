package com.modulith.petrolstats.stations.internal.infrastructure;

import org.springframework.web.service.annotation.GetExchange;

interface StationsRepositorySpringClient {
    @GetExchange("/EstacionesTerrestres/")
    RootResponse getStations();
}
