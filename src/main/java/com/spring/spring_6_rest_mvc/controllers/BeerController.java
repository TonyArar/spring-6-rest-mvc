package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
// base-path-mapping for all methods
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;


    // limit response to HTTP POST method
    // can also use convenience/shortcut-annotation @PostMapping
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveNewBeer(beer);
        //
        return new ResponseEntity(HttpStatus.CREATED);
    }

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
