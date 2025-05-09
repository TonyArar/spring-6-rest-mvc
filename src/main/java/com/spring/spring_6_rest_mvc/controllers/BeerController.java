package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
// base-path-mapping for all methods
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    // limit response to HTTP GET method
    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    // append path variable {beerID} to base-path and limit response to HTTP GET method
    @RequestMapping(path = "{beerID}", method = RequestMethod.GET)
    public Beer getBeerByID(@PathVariable("beerID") UUID id){

        log.debug("getBeerByID() called by BeerController controller");

        return beerService.getBeerByID(id);
    }

}
