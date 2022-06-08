package com.capgemini.bakery.taxonomy.region.controller;

import com.capgemini.bakery.taxonomy.region.model.Region;
import com.capgemini.bakery.taxonomy.region.model.mapper.RegionMapper;
import com.capgemini.bakery.taxonomy.region.repository.RegionRepository;
import com.capgemini.bakery.taxonomy.region.service.RegionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegionController.class)
class RegionControllerTest {

    @MockBean
    private RegionService regionService;
    @MockBean
    private RegionRepository regionRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private ArrayList<Region> regions;

    @BeforeEach
    void setupList() {
        regions = new ArrayList<>();
        Region munteniaRegion = new Region(10,"123MUN","Muntenia");
        regions.add(munteniaRegion);
    }

    @Test
    void getRegionByID() throws Exception {
        when(regionService.getRegionByID(regions.get(0).getRegion_id())).thenReturn(RegionMapper.toRegionDTO(regions.get(0)));
        mockMvc.perform(get("/bakery/v1/taxonomy/region/{id}",regions.get(0).getRegion_id())).andExpect(status().isOk())
                .andExpect(jsonPath("$.region_id").value(regions.get(0).getRegion_id()))
                .andExpect(jsonPath("$.code").value(regions.get(0).getCode()))
                .andExpect(jsonPath("$.name").value(regions.get(0).getName()))
                .andDo(print());
    }

    @Test
    void postRegion() {
    }

    @Test
    void getAllRegions() {

    }



    @Test
    void deleteByID() {
    }

    @Test
    void updateByID() {
    }

    @AfterEach
    void tearDown() {
    }
}