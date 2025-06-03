package com.spring.spring_6_rest_mvc.services;

import ch.qos.logback.core.util.StringUtil;
import com.github.javafaker.Faker;
import com.spring.spring_6_rest_mvc.entities.Beer;
import com.spring.spring_6_rest_mvc.mappers.BeerMapper;
import com.spring.spring_6_rest_mvc.models.BeerDTO;
import com.spring.spring_6_rest_mvc.models.BeerStyle;
import com.spring.spring_6_rest_mvc.repositories.BeerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    BeerRepository beerRepository;
    BeerMapper beerMapper;

    @Autowired
    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public List<BeerDTO> listBeers(){
        return beerRepository
                .findAll()
                .stream()
                .map(beer -> beerMapper.beerToBeerDTO(beer))
                .toList();
    }

    @Override
    public BeerDTO getBeerByID(UUID id) {
        return beerMapper.beerToBeerDTO(beerRepository.getReferenceById(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        Beer beerToBeSaved = Beer.builder()
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .build();
        Beer savedBeer = this.beerRepository.save(beerToBeSaved);
        return beerMapper.beerToBeerDTO(savedBeer);
    }

    @Override
    public void replaceById(UUID beerToBeReplacedId, BeerDTO newBeer) {

        Beer existingBeer = beerRepository.getReferenceById(beerToBeReplacedId);

        existingBeer.setBeerName(newBeer.getBeerName());
        existingBeer.setBeerStyle(newBeer.getBeerStyle());
        existingBeer.setUpc(newBeer.getUpc());
        existingBeer.setQuantityOnHand(newBeer.getQuantityOnHand());
        existingBeer.setPrice(newBeer.getPrice());
        existingBeer.setCreatedDate(LocalDateTime.now());
        existingBeer.setUpdateDate(LocalDateTime.now());

        beerRepository.save(existingBeer);

    }

    @Override
    public void removeById(UUID beerToBeRemovedId) {
        beerRepository.deleteById(beerToBeRemovedId);
    }

    @Override
    public void updateById(UUID beerToBeUpdatedId, BeerDTO beerPatchUpdate) {

        Beer beerToBeUpdated = beerRepository.getReferenceById(beerToBeUpdatedId);

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

        beerRepository.save(beerToBeUpdated);

    }

    @Override
    public boolean beerExists(UUID beerId) {
        return beerRepository.existsById(beerId);
    }

}
