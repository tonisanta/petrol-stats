package com.modulith.petrolstats.stations.internal.infrastructure.reader;

import org.springframework.web.service.annotation.GetExchange;

interface StationsRepositorySpringClient {
    @GetExchange("/EstacionesTerrestres/")
    RootResponse getStations();
}
