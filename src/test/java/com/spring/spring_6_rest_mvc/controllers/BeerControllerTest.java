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

import static org.hamcrest.core.Is.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

// restrict testing to controllers using WebMvcTest testing slice
@WebMvcTest(controllers = {BeerController.class})
class BeerControllerTest {

    // autowire MockMvc object that provides web app context (mark for spring with @Autowired)
    @Autowired
    MockMvc mockMvc;

    // mock beer service (controller dependency) with Mockito using BeerService interface (not the implementation) and @MockitoBean
    // mockito provides a mocked/fake implementation
    @MockitoBean
    BeerService beerService;

    // just for convenience, use the data that we have in the implementation
    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerByID() throws Exception {

        // getting data for stubbing
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        // stubbing mocked service
        given(beerService.getBeerByID(testBeer.getId())).willReturn(testBeer);

        // building request, performing it and performing expectations (similar to assertions) with matchers
        mockMvc.perform(get("/api/v1/beers/" + testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));

    }

}
