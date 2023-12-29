package com.modulith.petrolstats.stations.internal.infrastructure;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.DataNotAvailableException;
import com.modulith.petrolstats.stations.pricesbygeo.ComputePricesByGeo;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import com.modulith.petrolstats.stations.searchbyfilter.Filter;
import com.modulith.petrolstats.stations.searchbyfilter.GeoFilter;
import com.modulith.petrolstats.stations.searchbyfilter.SearchByFilter;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/stations")
public class StationsController {
    private final SearchByFilter searchByFilter;
    private final ComputePricesByGeo computePricesByGeo;

    public StationsController(SearchByFilter searchByFilter, ComputePricesByGeo computePricesByGeo) {
        this.searchByFilter = searchByFilter;
        this.computePricesByGeo = computePricesByGeo;
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
            Station[] response = searchByFilter.searchByFilter(filter);
            return ResponseEntity.ok(response);
        } catch (DataNotAvailableException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/aggregate/{geoCategory}")
    ResponseEntity<Map<String, StationPriceInfo>> getPricesAggregatedByGeo(@PathVariable GeoCategory geoCategory) {
        try {
            return ResponseEntity.ok(computePricesByGeo.computePricesByGeo(geoCategory));
        } catch (DataNotAvailableException e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}

