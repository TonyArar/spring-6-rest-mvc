package com.spring.spring_6_rest_mvc.services;

import com.github.javafaker.Faker;
import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.models.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl(){

        this.beerMap = new HashMap<>();
        Faker faker = new Faker();

        int numberOfGeneratedBeers = 5;

        for (int i = 1; i <= numberOfGeneratedBeers; i++){
            Random random = new Random();
            Beer beer = Beer.builder()
                    .id(UUID.randomUUID())
                    .version(random.nextInt(1,5))
                    .beerName(faker.beer().name())
                    .beerStyle(BeerStyle.values()[random.nextInt(0, 10)])
                    .upc(faker.code().gtin8())
                    .quantityOnHand(random.nextInt(0, 101))
                    .price(BigDecimal.valueOf(random.nextDouble() * 100))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            this.beerMap.put(beer.getId(), beer);
        }

    }

    @Override
    public List<Beer> listBeers(){
        return new ArrayList<Beer>(beerMap.values());
    }

    @Override
    public Beer getBeerByID(UUID id) {
        return beerMap.get(id);
    }

}
