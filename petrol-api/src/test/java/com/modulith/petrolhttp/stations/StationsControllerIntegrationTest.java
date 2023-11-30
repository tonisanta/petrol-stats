package com.modulith.petrolhttp.stations;

import com.modulith.petrolstats.geography.GeoCategory;
import com.modulith.petrolstats.stations.DataNotAvailableException;
import com.modulith.petrolstats.stations.pricesbygeo.ComputePricesByGeo;
import com.modulith.petrolstats.stations.pricesbygeo.StationPriceInfo;
import com.modulith.petrolstats.stations.searchbyfilter.Filter;
import com.modulith.petrolstats.stations.searchbyfilter.GeoFilter;
import com.modulith.petrolstats.stations.searchbyfilter.SearchByFilter;
import com.modulith.petrolstats.stations.searchbyfilter.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class StationsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchByFilter searchByFilter;

    // TODO: add tests for prices by geo
    @MockBean
    private ComputePricesByGeo computePricesByGeo;

    @Test
    void shouldReturnStationsAsJsonAnd200() throws Exception {
        var stations = new Station[]{
                new Station("1", "cityA", "provinceA", "communityA",
                        new StationPriceInfo(1.0, 2.3, 3.5, 4.0))
        };

        String expectedJson = """
                [
                   {
                      "id":"1",
                      "cityId":"cityA",
                      "provinceId":"provinceA",
                      "communityId":"communityA",
                      "priceInfo":{
                         "petrol95":1.0,
                         "petrol98":2.3,
                         "diesel":3.5,
                         "dieselPremium":4.0
                      }
                   }
                ]
                """;

        when(searchByFilter.searchByFilter(null)).thenReturn(stations);
        mockMvc.perform(get("/stations"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldCreateAFilter() throws Exception {
        var stations = new Station[]{
                new Station("1", "cityA", "provinceA", "communityA",
                        new StationPriceInfo(1.0, 2.3, 3.5, 4.0))
        };

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("geoCategory", "PROVINCE");
        params.addAll("ids", Arrays.asList("07", "19"));

        // This filter must be created based on the query parameters
        var filter = new Filter(new GeoFilter(GeoCategory.PROVINCE, Set.of("07", "19")));
        when(searchByFilter.searchByFilter(filter)).thenReturn(stations);

        mockMvc.perform(
                        get("/stations")
                                .queryParams(params)
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn503WhenNoDataAvailable() throws Exception {
        when(searchByFilter.searchByFilter(null)).thenThrow(DataNotAvailableException.class);
        mockMvc.perform(get("/stations"))
                .andExpect(status().isServiceUnavailable());
    }

}
