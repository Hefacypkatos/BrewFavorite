package com.example.BrewFavorite.controller;
import com.example.BrewFavorite.model.BeverageEntity;
import com.example.BrewFavorite.repository.BeverageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class BeverageControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeverageRepository beverageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private BeverageEntity testBeverage;

    @BeforeEach
    void setup() {
        beverageRepository.deleteAll(); // Clean the database before each test

        testBeverage = new BeverageEntity();
        testBeverage.setName("Espresso");
        testBeverage.setBeverageType("Coffee");
        testBeverage.setAlcoholVol(0.0);
        testBeverage.setManufactureDate(2024);

        beverageRepository.save(testBeverage);
    }

    @Test
    void shouldGetAllBeverages() throws Exception {
        mockMvc.perform(get("/beverages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1)) // Expecting 1 beverage in list
                .andExpect(jsonPath("$[0].name").value("Espresso"));
    }

    @Test
    void shouldGetBeverageById() throws Exception {
        mockMvc.perform(get("/beverages/" + testBeverage.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Espresso"));
    }

    @Test
    void shouldReturnNotFoundForNonExistingBeverage() throws Exception {
        mockMvc.perform(get("/beverages/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewBeverage() throws Exception {
        BeverageEntity newBeverage = BeverageEntity.builder()
                .name("Latte")
                .beverageType("Coffe")
                .alcoholVol(0.0)
                .manufactureDate(2024)
                .build();

        mockMvc.perform(post("/beverages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBeverage)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Latte"));
    }

    @Test
    void shouldDeleteBeverage() throws Exception {
        mockMvc.perform(delete("/beverages/" + testBeverage.getId()))
                .andExpect(status().isNoContent());

        Optional<BeverageEntity> deleted = beverageRepository.findById(testBeverage.getId());
        assert deleted.isEmpty();
    }


}
