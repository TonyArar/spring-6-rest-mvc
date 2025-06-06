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


    // TODO: rename params to match semantics
    @Query(value = "SELECT b " +
            "FROM Beer b " +
            "WHERE ( :beerName IS NULL OR lower(b.beerName) LIKE lower(concat('%', ?1,'%')) ) " +
            "AND ( :beerStyle IS NULL OR b.beerStyle = ?2 ) " +
            "AND ( :upc IS NULL OR b.upc = ?3 ) " +
            "AND ( :quantityOnHand IS NULL OR ?4 <= b.quantityOnHand ) " +
            "AND ( :price IS NULL OR b.price <= ?5 )"
    )
    List<Beer> findAllByQueryParams(String beerName, BeerStyle beerStyle, String upc, Integer quantityOnHand, BigDecimal price);


}
