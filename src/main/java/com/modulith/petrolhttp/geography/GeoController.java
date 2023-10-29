package com.modulith.petrolhttp.geography;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.geography.GeoResponse;
import com.modulith.petrolstats.geography.GeoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geo")
public class GeoController {
    private final GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping("/cities")
    GeoResponse[] getCities() {
        return geoService.getAll(GeoCategory.CITY);
    }

    @GetMapping("/provinces")
    GeoResponse[] getProvinces() {
        return geoService.getAll(GeoCategory.PROVINCE);
    }

    @GetMapping("/communities")
    GeoResponse[] getCommunities() {
        return geoService.getAll(GeoCategory.AUTONOMOUS_COMMUNITY);
    }
}
