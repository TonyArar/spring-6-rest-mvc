package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.bootstrap_data.DatabasePopulate;
import com.spring.spring_6_rest_mvc.entities.Beer;
import com.spring.spring_6_rest_mvc.exception_handling.ResourceNotFoundException;
import com.spring.spring_6_rest_mvc.models.BeerDTO;
import com.spring.spring_6_rest_mvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    void repopulateDatabase() throws Exception {
        beerRepository.deleteAll();
        databasePopulate.run(null);
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