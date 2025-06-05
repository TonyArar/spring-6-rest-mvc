package com.spring.spring_6_rest_mvc.repositories;

import com.spring.spring_6_rest_mvc.entities.Beer;
import com.spring.spring_6_rest_mvc.models.BeerDTO;
import com.spring.spring_6_rest_mvc.models.BeerStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {


    // TODO: implement query
    @NativeQuery()
    List<BeerDTO> findAllByQueryParams(String beerName, BeerStyle beerStyle, String upc, Integer quantityOnHand, BigDecimal price);


}
