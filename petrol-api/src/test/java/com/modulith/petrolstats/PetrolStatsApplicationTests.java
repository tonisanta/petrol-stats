package com.modulith.petrolstats;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.modulith.core.ApplicationModules;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class PetrolStatsApplicationTests {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mariadb = new MySQLContainer<>("mysql:8.2.0")
            .withDatabaseName("petrolstations");

    @Test
    void contextLoads() {
    }

    @Test
    void verifyModuleStructure() {
        ApplicationModules.of(PetrolStatsApplication.class).verify();
    }
}
