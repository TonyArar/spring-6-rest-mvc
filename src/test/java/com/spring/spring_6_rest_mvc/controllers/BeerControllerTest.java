package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.services.BeerService;
import com.spring.spring_6_rest_mvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = {BeerController.class})
class BeerControllerTest {

    // MockMvc
    @Autowired
    MockMvc mockMvc;

    // Mockito MockBean
    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerByID() throws Exception {

        Beer testBeer = beerServiceImpl.listBeers().get(0);

        // use Mockito (BDDMockito) to mock data for beer service
        // enforces a return value (data) on any method call in given()
        // using any() and willReturn()
        given(beerService.getBeerByID(any(UUID.class))).willReturn(testBeer);

        // use MockMvc to perform a mocked http GET request (built with MockMvcRequestBuilders)
        // set accept header of mocked http GET request to json (application/json)
        // perform the request using MockMvc
        // then perform 2 expectation on the returned response (ResultActions object) using MockMvcResultMatchers
        // first is that we get a OK HTTP status code (200)
        // second is that the content type (body content type) is json
        mockMvc.perform(get("/api/v1/beers/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

}
