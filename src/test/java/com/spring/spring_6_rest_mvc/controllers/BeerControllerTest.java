package com.spring.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.spring_6_rest_mvc.models.Beer;
import com.spring.spring_6_rest_mvc.services.BeerService;
import com.spring.spring_6_rest_mvc.services.BeerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    // spring can autowire a jackson ObjectMapper
    @Autowired
    ObjectMapper objectMapper;

    // mock beer service (controller dependency) with Mockito using BeerService interface (not the implementation) and @MockitoBean
    // mockito provides a mocked/fake implementation
    @MockitoBean
    BeerService beerService;

    // just for convenience, use the data that we have in the implementation
    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp(){
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void testUpdateBeer() throws Exception {

        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(patch("/api/v1/beers/" + beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        // verifies that a certain behavior happened n number of times
        // verify(mock, times(1)).someMethod("some arg");
        // we use argument matchers here, but the dude in the course said
        // that we will be using argument captors later
        verify(beerService).updateById(any(UUID.class), any(Beer.class));
    }

    @Test
    void testCreateNewBeer() throws Exception {

        Beer newBeer = beerServiceImpl.listBeers().get(0);
        newBeer.setVersion(null);
        newBeer.setId(null);

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post("/api/v1/beers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBeer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void listBeers() throws Exception {

        List<Beer> beerList = beerServiceImpl.listBeers();

        given(beerService.listBeers()).willReturn(beerList);

        mockMvc.perform(get("/api/v1/beers").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(5)));

    }

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
