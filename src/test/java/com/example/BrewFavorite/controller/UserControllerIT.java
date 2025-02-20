package com.example.BrewFavorite.controller;

import com.example.BrewFavorite.model.FavoriteBeveragesEntity;
import com.example.BrewFavorite.model.UserEntity;
import com.example.BrewFavorite.repository.FavoriteBeveragesRepository;
import com.example.BrewFavorite.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FavoriteBeveragesRepository favoriteBeveragesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity testUser;
    private FavoriteBeveragesEntity testFavoriteBrew;

    @BeforeEach
    void setup() {
        userRepository.deleteAll(); //

        testUser = new UserEntity();
        testFavoriteBrew = new FavoriteBeveragesEntity();
        testUser.setUsername("Anal-fabeta");
        testUser.setPassword("password123");
        testUser.setFavoriteBeverages(testFavoriteBrew);


        userRepository.save(testUser);
        favoriteBeveragesRepository.save(testFavoriteBrew);
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].username").value("Anal-fabeta"));
    }

    @Test
    void shouldGetUserByID() throws Exception {
        mockMvc.perform(get("/users/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Anal-fabeta"));
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        UserEntity newUser = UserEntity.builder()
                .username("Anal-ityk")
                .password("password321")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Anal-ityk"));

    }

    @Test
    void shouldDeleteUserByID() throws Exception {
        mockMvc.perform(delete("/users/" + testUser.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundForNonExistingUser() throws Exception {
        mockMvc.perform(get("/users/999"))
                .andExpect(status().isNotFound());

    }
}
