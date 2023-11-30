package com.modulith.petrolstats;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class DocumentationTests {
    ApplicationModules modules = ApplicationModules.of(PetrolStatsApplication.class);

    @Test
    void writeDocumentationSnippets() {

        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }
}

