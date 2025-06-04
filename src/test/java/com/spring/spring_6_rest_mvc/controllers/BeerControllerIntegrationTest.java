package com.spring.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.spring_6_rest_mvc.bootstrap_data.DatabasePopulate;
import com.spring.spring_6_rest_mvc.entities.Beer;
import com.spring.spring_6_rest_mvc.exception_handling.ResourceNotFoundException;
import com.spring.spring_6_rest_mvc.models.BeerDTO;
import com.spring.spring_6_rest_mvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest for integration testing, brings in everything into the context
// integration testing for BeerController:
// testing the beer controller in a full application context,
// meaning testing the controller and its interaction with other components/beans
// (e.g. interacting with services to get data from database, here bootstrapped H2 intended)
@SpringBootTest
class BeerControllerIntegrationTest {

    @Autowired
    DatabasePopulate databasePopulate;

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUpMockMvc(){
        // build a MockMvc object and set/hook up its environment with
        // the full WebApplicationContext that @SpringBootTest provides
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    void repopulateDatabase() throws Exception {
        beerRepository.deleteAll();
        beerRepository.flush();
        databasePopulate.run(null);
    }

    @Test
    void testUpdateBeerBadName() throws Exception {

        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerPatch = new HashMap<>();
        beerPatch.put("beerName", "TEST NAME longer than 50 charsTEST NAME longer than 50 chars");

        mockMvc.perform(patch(BeerController.PATH_BEER_BY_ID, beer.getId())
                        .content(objectMapper.writeValueAsString(beerPatch))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testGetBeerByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            beerController.getBeerByID(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        BeerDTO beerDTO = beerController.listBeers().get(0);
        Beer beer = beerRepository.findAll().get(0);
        assertEquals(beerDTO.getId(), beer.getId());
    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtoList = beerController.listBeers();
        assertEquals(5,dtoList.size());
    }

    // convenience annotations
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtoList = beerController.listBeers();
        assertEquals(0,dtoList.size());
    }


}