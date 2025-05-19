package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.services.BeerService;
import lombok.AllArgsConstructor;
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
// base-path-mapping for all methods
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    @PatchMapping("{beerToBeUpdatedId}")
    public ResponseEntity updateBeerById(@PathVariable("beerToBeUpdatedId") UUID beerToBeUpdatedId, @RequestBody Beer beerPatchUpdate){
        beerService.updateById(beerToBeUpdatedId, beerPatchUpdate);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{beerToBeRemovedId}")
    public ResponseEntity removeBeerById(@PathVariable("beerToBeRemovedId") UUID beerToBeRemovedId){
        beerService.removeById(beerToBeRemovedId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // FIXME: handle case: non-existing resource
    @PutMapping("{beerToBeReplacedId}")
    public ResponseEntity replaceBeerById(@PathVariable("beerToBeReplacedId") UUID beerToBeReplacedId, @RequestBody Beer newBeer){

        try {
            beerService.replaceById(beerToBeReplacedId, newBeer);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception resourceNotFoundException){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    // limit response to HTTP POST method
    @PostMapping
    public ResponseEntity handlePost(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveNewBeer(beer);

        // best practice:
        // created headers for response to return a location header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/beers/" + savedBeer.getId());

        // HttpStatus.CREATED = http response status code 201 created
        // add headers to response
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    // limit response to HTTP GET method
    @GetMapping
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }

    // append path variable {beerID} to base-path and limit response to HTTP GET method
    @GetMapping("{beerID}")
    public Beer getBeerByID(@PathVariable("beerID") UUID id){

        log.debug("getBeerByID() called by BeerController controller");

        return beerService.getBeerByID(id);
    }

}
