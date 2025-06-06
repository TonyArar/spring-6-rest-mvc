package com.spring.spring_6_rest_mvc.services;

import com.spring.spring_6_rest_mvc.models.BeerDTO;
import com.spring.spring_6_rest_mvc.models.BeerStyle;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeersByQueryParams(String beerName,
                            BeerStyle beerStyle,
                            String upc,
                            Integer quantityOnHand,
                            BigDecimal price);

    BeerDTO getBeerByID(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    void replaceById(UUID beerToBeReplacedId, BeerDTO newBeer);

    void removeById(UUID beerToBeRemovedId);

    void updateById(UUID beerToBeUpdatedId, BeerDTO beerPatchUpdate);

    boolean beerExists(UUID beerId);

}
