package com.modulith.petrolhttp.stations;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.*;
import com.modulith.petrolstats.stations.DataNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/stations")
public class StationsController {
    private final StationsService stationsService;

    public StationsController(StationsService stationsService) {
        this.stationsService = stationsService;
    }

    @GetMapping()
    ResponseEntity<Station[]> getStations(@RequestParam(required = false) GeoCategory geoCategory,
                                          @RequestParam(required = false) Set<String> ids) {
        Filter filter = null;
        if (geoCategory != null) {
            if (ids == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            filter = new Filter(new GeoFilter(geoCategory, ids));
        }

        try {
            Station[] response = stationsService.getByFilter(filter);
            return ResponseEntity.of(Optional.of(response));
        } catch (DataNotAvailableException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/aggregate")
    Map<String, StationPriceInfo> getPricesAggregatedByGeo(@RequestParam GeoCategory geoCategory) {
        return stationsService.getPricesAggregatedByGeo(geoCategory);
    }
}
