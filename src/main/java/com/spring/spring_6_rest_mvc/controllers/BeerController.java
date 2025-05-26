package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    // URIs/paths and co.
    public static final String PATH_ALL_BEERS = "/api/v1/beers";
    public static final String PATHVAR_BEER_ID = "beerID";
    public static final String PATH_BEER_BY_ID =
            PATH_ALL_BEERS + "/{" + PATHVAR_BEER_ID + "}";

    private final BeerService beerService;

    @PatchMapping(PATH_BEER_BY_ID)
    public ResponseEntity updateBeerById(@PathVariable(PATHVAR_BEER_ID) UUID beerToBeUpdatedId, @RequestBody Beer beerPatchUpdate){
        beerService.updateById(beerToBeUpdatedId, beerPatchUpdate);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATH_BEER_BY_ID)
    public ResponseEntity removeBeerById(@PathVariable(PATHVAR_BEER_ID) UUID beerToBeRemovedId){
        beerService.removeById(beerToBeRemovedId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // FIXME: handle case: non-existing resource
    @PutMapping(PATH_BEER_BY_ID)
    public ResponseEntity replaceBeerById(@PathVariable(PATHVAR_BEER_ID) UUID beerToBeReplacedId, @RequestBody Beer newBeer){

        try {
            beerService.replaceById(beerToBeReplacedId, newBeer);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception resourceNotFoundException){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(PATH_ALL_BEERS)
    public ResponseEntity createBeer(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveNewBeer(beer);

        // best practice:
        // created headers for response to return a location header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/beers/" + savedBeer.getId());

        // HttpStatus.CREATED = http response status code 201 created
        // add headers to response
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(PATH_ALL_BEERS)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    @GetMapping(PATH_BEER_BY_ID)
    public Beer getBeerByID(@PathVariable(PATHVAR_BEER_ID) UUID id){

        log.debug("getBeerByID() called by BeerController controller");

        return beerService.getBeerByID(id);
    }

}
