package com.capgemini.bakery.taxonomy.area.controller;

import com.capgemini.bakery.taxonomy.area.model.Area;
import com.capgemini.bakery.taxonomy.area.model.mapper.AreaMapper;
import com.capgemini.bakery.taxonomy.area.repository.AreaRepository;
import com.capgemini.bakery.taxonomy.area.service.AreaService;
import com.capgemini.bakery.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AreaController.class)
class AreaControllerTest {

    @MockBean
    private AreaService areaService;
    @MockBean
    private AreaRepository areaRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAddArea() throws Exception {
        Area area = new Area(1, "007", "Craiova");
        mockMvc.perform(post("/bakery/v1/taxonomy/area/").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(area)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnArea() throws Exception {
        int id = 1;
        Area area = new Area(id, "007", "Craiova");
        when(areaService.getAreaByID(id)).thenReturn(AreaMapper.toAreaDTO(area));
        mockMvc.perform(get("/bakery/v1/taxonomy/area/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.areaId").value(id))
                .andExpect(jsonPath("$.code").value(area.getCode()))
                .andExpect(jsonPath("$.name").value(area.getName()))
                .andDo(print());
    }


    @Test
    void shouldReturnNotFoundArea() throws Exception {
        int id = 1;
        when(areaService.getAreaByID(id)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/bakery/v1/taxonomy/area/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    void shouldReturnListOfArea() throws Exception {
        List<Area> areas = new ArrayList<>(
                Arrays.asList(new Area(1, "007", "Craiova"),
        new Area(1, "007", "Craiova"),
        new Area(1, "007", "Craiova")));
        when(areaService.getAllAreas()).thenReturn(areas.stream().map(AreaMapper::toAreaDTO).collect(Collectors.toList()));
        mockMvc.perform(get("/bakery/v1/taxonomy/area/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(areas.size()))
                .andDo(print());
    }


    @Test
    void shouldUpdateArea() throws Exception {
        int id = 1;
        Area area = new Area(id, "007", "Craiova");
        Area updatedArea = new Area(id, "008", "Craiova");
        when(areaService.getAreaByID(id)).thenReturn(AreaMapper.toAreaDTO(area));
//        when(areaRepository.save(any(Area.class))).thenReturn(updatedArea);
        when(areaService.updateAreaByID(id, AreaMapper.toAreaDTO(updatedArea))).thenReturn(AreaMapper.toAreaDTO(updatedArea));
        mockMvc.perform(put("/bakery/v1/taxonomy/area/{id}", id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedArea)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(updatedArea.getCode()))
                .andExpect(jsonPath("$.name").value(updatedArea.getName()))
                .andDo(print());
    }


    @Test
    void shouldDeleteArea() throws Exception {
        int id = 1;
        Area area = new Area(id, "007", "Craiova");
        when(areaService.deleteByID(id)).thenReturn(AreaMapper.toAreaDTO(area));
        mockMvc.perform(delete("/bakery/v1/taxonomy/area/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }




}