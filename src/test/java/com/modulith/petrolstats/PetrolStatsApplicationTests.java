package com.modulith.petrolstats;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class PetrolStatsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void verifyModuleStructure() {
        ApplicationModules.of(PetrolStatsApplication.class).verify();
    }
}
