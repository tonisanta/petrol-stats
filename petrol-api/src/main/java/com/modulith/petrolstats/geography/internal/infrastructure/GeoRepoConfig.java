package com.modulith.petrolstats.geography.internal.infrastructure;

import com.modulith.petrolstats.httpclient.ClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.time.Duration;

@Configuration
class GeoRepoConfig {
    @Bean
    public GeoRepositorySpringClient createGeoRepositorySpringClient() throws SSLException {
        var baseUrl = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/PreciosCarburantes/Listados";
        return ClientFactory.createClient(GeoRepositorySpringClient.class, Duration.ofSeconds(30), baseUrl);
    }

}
