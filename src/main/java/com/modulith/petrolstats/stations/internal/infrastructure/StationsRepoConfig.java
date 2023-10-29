package com.modulith.petrolstats.stations.internal.infrastructure;

import com.modulith.petrolstats.common.ClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.time.Duration;
import java.util.Timer;

@Configuration
class StationsRepoConfig {
    @Bean
    public StationsRepositorySpringClient createStationsRepositorySpringClient() throws SSLException {
        var baseUrl = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes";
        return ClientFactory.createClient(StationsRepositorySpringClient.class, Duration.ofSeconds(10), baseUrl);
    }

}
