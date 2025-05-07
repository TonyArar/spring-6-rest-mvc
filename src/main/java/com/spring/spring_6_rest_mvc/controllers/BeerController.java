package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;

    public Beer getBeerByID(UUID id){

        log.debug("getBeerByID() called by BeerController controller");

        return beerService.getBeerByID(id);
    }

}
