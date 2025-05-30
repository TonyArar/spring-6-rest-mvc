package com.spring.spring_6_rest_mvc.services;

import com.spring.spring_6_rest_mvc.models.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    BeerDTO getBeerByID(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    void replaceById(UUID beerToBeReplacedId, BeerDTO newBeer);

    void removeById(UUID beerToBeRemovedId);

    void updateById(UUID beerToBeUpdatedId, BeerDTO beerPatchUpdate);

    boolean beerExists(UUID beerId);

}
