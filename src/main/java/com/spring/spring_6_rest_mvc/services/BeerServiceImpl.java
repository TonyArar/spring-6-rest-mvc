package com.spring.spring_6_rest_mvc.services;

import ch.qos.logback.core.util.StringUtil;
import com.github.javafaker.Faker;
import com.spring.spring_6_rest_mvc.models.BeerDTO;
import com.spring.spring_6_rest_mvc.models.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl(){

        this.beerMap = new HashMap<>();
        Faker faker = new Faker();

        int numberOfGeneratedBeers = 5;

        for (int i = 1; i <= numberOfGeneratedBeers; i++){
            Random random = new Random();
            BeerDTO beer = BeerDTO.builder()
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
    public List<BeerDTO> listBeers(){
        return new ArrayList<BeerDTO>(beerMap.values());
    }

    @Override
    public BeerDTO getBeerByID(UUID id) {
        return beerMap.get(id);
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        // build beer from received beer to give id and dates
        // because we won't get that from client request
        BeerDTO beerToBeSaved = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .build();
        // save beer
        beerMap.put(beerToBeSaved.getId(), beerToBeSaved);
        return beerToBeSaved;
    }

    @Override
    public void replaceById(UUID beerToBeReplacedId, BeerDTO newBeer) {

        BeerDTO existingBeer = beerMap.get(beerToBeReplacedId);

        existingBeer.setBeerName(newBeer.getBeerName());
        existingBeer.setBeerStyle(newBeer.getBeerStyle());
        existingBeer.setUpc(newBeer.getUpc());
        existingBeer.setQuantityOnHand(newBeer.getQuantityOnHand());
        existingBeer.setPrice(newBeer.getPrice());
        existingBeer.setCreatedDate(LocalDateTime.now());
        existingBeer.setUpdateDate(LocalDateTime.now());

    }

    @Override
    public void removeById(UUID beerToBeRemovedId) {
        beerMap.remove(beerToBeRemovedId);
    }

    @Override
    public void updateById(UUID beerToBeUpdatedId, BeerDTO beerPatchUpdate) {
        BeerDTO beerToBeUpdated = beerMap.get(beerToBeUpdatedId);

        String newName = beerPatchUpdate.getBeerName();
        BeerStyle newStyle = beerPatchUpdate.getBeerStyle();
        String newUPC = beerPatchUpdate.getUpc();
        Integer newQuantityOnHand = beerPatchUpdate.getQuantityOnHand();
        BigDecimal newPrice = beerPatchUpdate.getPrice();

        // predicates
        Predicate<String> hasText = StringUtil::notNullNorEmpty;
        Predicate<Object> notNull = Objects::nonNull;

        if (hasText.test(newName)) {
            beerToBeUpdated.setBeerName(newName);
        }

        if (notNull.test(newStyle)){
            beerToBeUpdated.setBeerStyle(newStyle);
        }

        if (hasText.test(newUPC)){
            beerToBeUpdated.setUpc(newUPC);
        }

        if (newQuantityOnHand >= 0){
            beerToBeUpdated.setQuantityOnHand(newQuantityOnHand);
        }

        if (notNull.test(newPrice)){
            beerToBeUpdated.setPrice(newPrice);
        }

        beerToBeUpdated.setUpdateDate(LocalDateTime.now());

    }

    @Override
    public boolean beerExists(UUID beerId) {
        return beerMap.get(beerId) != null;
    }

}
