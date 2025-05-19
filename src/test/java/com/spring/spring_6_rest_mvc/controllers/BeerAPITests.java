package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.services.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerAPITests {

    @Autowired
    BeerService beerService;

    @Test
    void updateBeerById() {
        //Beer newBeer = Beer.builder().build();
        //assertNotNull(beerService.saveNewBeer());
    }

    @Test
    void removeBeerById() {

    }

    @Test
    void replaceBeerById() {

    }

    @Test
    void handlePost() {

    }

    @Test
    void listBeers() {

    }

    @Test
    void getBeerByID() {

    }

}
