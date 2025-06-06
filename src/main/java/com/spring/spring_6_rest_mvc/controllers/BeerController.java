package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.entities.Beer;
import com.spring.spring_6_rest_mvc.exception_handling.ResourceNotFoundException;
import com.spring.spring_6_rest_mvc.models.BeerDTO;
import com.spring.spring_6_rest_mvc.models.BeerStyle;
import com.spring.spring_6_rest_mvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {


    // URIs/paths and co.

    public static final String PATH_ALL_BEERS = "/api/v1/beers";
    public static final String PATHVAR_BEER_ID = "beerID";

    // "/api/v1/beers/{beerID}"
    public static final String PATH_BEER_BY_ID =
            PATH_ALL_BEERS + "/{" + PATHVAR_BEER_ID + "}";


    // Dependencies

    private final BeerService beerService;


    // REST API Endpoints

    @PatchMapping(PATH_BEER_BY_ID)
    public ResponseEntity updateBeerById(@PathVariable(PATHVAR_BEER_ID) UUID beerToBeUpdatedId, @RequestBody BeerDTO beerPatchUpdate){
        if (!beerService.beerExists(beerToBeUpdatedId)) throw new ResourceNotFoundException();
        beerService.updateById(beerToBeUpdatedId, beerPatchUpdate);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATH_BEER_BY_ID)
    public ResponseEntity removeBeerById(@PathVariable(PATHVAR_BEER_ID) UUID beerToBeRemovedId){
        if (!beerService.beerExists(beerToBeRemovedId)) throw new ResourceNotFoundException();
        beerService.removeById(beerToBeRemovedId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(PATH_BEER_BY_ID)
    public ResponseEntity replaceBeerById(@PathVariable(PATHVAR_BEER_ID) UUID beerToBeReplacedId,
                                          @Validated @RequestBody BeerDTO newBeer){
        if (!beerService.beerExists(beerToBeReplacedId)) throw new ResourceNotFoundException();
        beerService.replaceById(beerToBeReplacedId, newBeer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(PATH_ALL_BEERS)
    public ResponseEntity createBeer(@Validated @RequestBody BeerDTO beer){
        BeerDTO savedBeer = beerService.saveNewBeer(beer);
        // best practice:
        // created headers for response to return a location header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/beers/" + savedBeer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(PATH_ALL_BEERS)
    public List<BeerDTO> listBeersByQueryParams(@RequestParam(required = false) String beerName,
                                   @RequestParam(required = false) BeerStyle beerStyle,
                                   @RequestParam(required = false) String upc,
                                   @RequestParam(required = false) Integer quantityOnHand,
                                   @RequestParam(required = false) BigDecimal price){
        // any request parameter which isn't provided is null, when all are null,
        // the implementation should simply return all beers without filtering
        List<BeerDTO> result = beerService.listBeersByQueryParams(beerName, beerStyle, upc, quantityOnHand, price);
        return result;
    }

    @GetMapping(PATH_BEER_BY_ID)
    public BeerDTO getBeerByID(@PathVariable(PATHVAR_BEER_ID) UUID id){
        if (!beerService.beerExists(id)) throw new ResourceNotFoundException();
        return beerService.getBeerByID(id);
    }


}
