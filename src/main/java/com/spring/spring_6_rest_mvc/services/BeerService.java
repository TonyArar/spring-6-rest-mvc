package com.spring.spring_6_rest_mvc.services;

import com.spring.spring_6_rest_mvc.models.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerByID(UUID id);

}
