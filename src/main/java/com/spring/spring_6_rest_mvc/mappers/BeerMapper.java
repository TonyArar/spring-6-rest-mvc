package com.spring.spring_6_rest_mvc.mappers;

import com.spring.spring_6_rest_mvc.entities.Beer;
import com.spring.spring_6_rest_mvc.models.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    BeerDTO beerToBeerDTO(Beer beer);

    Beer beerDTOtoBeer(BeerDTO beerDTO);

}
