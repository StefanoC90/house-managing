package com.oneandone.housemanaging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneandone.housemanaging.model.House;
import com.oneandone.housemanaging.service.HouseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseController.class)
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseService service;

    @Test
    void getHouse() throws Exception {
        when(service.getAllHouse(false)).thenReturn(new ArrayList<>());
        MvcResult result = mockMvc.perform(get("/rest/house")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("[]");
    }

    @Test
    void getHouseById() throws Exception {
        House house = new House();
        house.setOwnerName("John");
        house.setId(1);
        when(service.getHouseById(1)).thenReturn(house);
        MvcResult result = mockMvc.perform(get("/rest/house/1")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("{\"id\":1,\"ownerName\":\"John\"}");
    }

    @Test
    void createHouse() throws Exception {
        House house = new House();
        house.setId(1);
        house.setSize(1D);
        house.setOwnerName("John");
        house.setAddress("Munich");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(house);
        when(service.createHouse(any())).thenReturn(house);

        MvcResult result = mockMvc.perform(post("/rest/house")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("{\"id\":1,\"size\":1.0,\"address\":\"Munich\",\"ownerName\":\"John\"}");
    }

    @Test
    void createHouseWithError() throws Exception {
        House house = new House();
        house.setId(1);
        house.setSize(-1D);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(house);
        when(service.createHouse(house)).thenReturn(house);

        MvcResult result = mockMvc.perform(post("/rest/house")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .contains("Address is required", "Owner Name is required", "The size should have a positive value");
    }

    @Test
    void updateHouse() throws Exception {
        House house = new House();
        house.setId(1);
        house.setSize(1D);
        house.setOwnerName("John");
        house.setAddress("Munich");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(house);
        when(service.updateHouse(any(), anyInt())).thenReturn(house);

        MvcResult result = mockMvc.perform(put("/rest/house/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("{\"id\":1,\"size\":1.0,\"address\":\"Munich\",\"ownerName\":\"John\"}");

    }
    @Test
    void updateHouseWithValidationError() throws Exception {
        House house = new House();
        house.setId(1);
        house.setSize(-1D);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(house);
        when(service.createHouse(house)).thenReturn(house);

        MvcResult result = mockMvc.perform(put("/rest/house/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .contains("Address is required", "Owner Name is required", "The size should have a positive value");
    }

    @Test
    void deleteHouse() throws Exception {
         mockMvc.perform(delete("/rest/house/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
