package com.spring.spring_6_rest_mvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerTest {

    @Autowired
    BeerController beerController;

    @Test
    void getBeerByID() {

        System.out.println(beerController.getBeerByID(UUID.randomUUID()));

    }

}