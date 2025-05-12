package com.spring.spring_6_rest_mvc.services;

import com.spring.spring_6_rest_mvc.models.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Beer getBeerByID(UUID id);

    Beer saveNewBeer(Beer beer);

    void replaceById(UUID beerToBeReplacedId, Beer newBeer);
}
