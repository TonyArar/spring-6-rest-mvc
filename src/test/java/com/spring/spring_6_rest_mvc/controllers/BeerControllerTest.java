package com.spring.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.spring_6_rest_mvc.models.BeerDTO;
import com.spring.spring_6_rest_mvc.services.BeerService;
import com.spring.spring_6_rest_mvc.services.BeerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.core.Is.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

// TODO: rewrite tests
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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @BeforeEach
    void setUp(){
        beerServiceImpl = new BeerServiceImpl();
    }


    @Test
    void testNonExistingBeer() throws Exception {

        given(beerService.beerExists(any(UUID.class))).willReturn(false);

        UUID nonExistingID = UUID.randomUUID();

        mockMvc.perform(get(BeerController.PATH_BEER_BY_ID, nonExistingID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(beerService).beerExists(uuidArgumentCaptor.capture());

        assertEquals(nonExistingID,uuidArgumentCaptor.getValue());

    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        // we don't have to provide an entire beer object for Jackson
        // we can mimic what a client would send as a patch with a map
        // and then marshall it with the ObjectMapper
        Map<String, Object> beerPatch = new HashMap<>();
        beerPatch.put("beerName", "TEST NAME");

        given(beerService.beerExists(any(UUID.class))).willReturn(true);

        mockMvc.perform(patch(BeerController.PATH_BEER_BY_ID, beer.getId())
                        .content(objectMapper.writeValueAsString(beerPatch))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // make sure that updateById() was called after performing the request
        // and capture the passed arguments
        verify(beerService).updateById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        // make sure that the argument (ID) passed to updateById() is the same as the one in the URI
        assertEquals(beer.getId(), uuidArgumentCaptor.getValue());

        // make sure that the argument (beer name) passed to updateById() is the same as in the request body
        assertEquals(beerPatch.get("beerName"), beerArgumentCaptor.getValue().getBeerName());

    }

    @Test
    void testRemoveBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        given(beerService.beerExists(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(BeerController.PATH_BEER_BY_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // using an argument captor:
        // captor listens on the mock for passed arguments,
        // later we get the value passed and can perform assertions/verifications
        // for example to make sure the right value was passed
        // this is useful for example if we have a lot of layers/objects the passed data
        // is going through, and we want to make sure that it is not mutated for example
        // or that the right arguments are produced/passed by the last layer for example
        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        // verify that behaviour happened for any argument
        // verify(beerServiceImpl).removeById(any(UUID.class));

        // verify that behaviour happened for passed argument
        // capture() MUST be used inside of verification
        // capture() stores (captures) the argument value so that we can use it
        // later to perform assertions
        verify(beerService).removeById(uuidArgumentCaptor.capture());

        // assert that passed (captured) argument is equal to what we expected to be passed
        // getValue() gets captured argument value
        assertEquals(beer.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    void testReplaceBeer() throws Exception {

        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        given(beerService.beerExists(any(UUID.class))).willReturn(true);

        mockMvc.perform(put(BeerController.PATH_BEER_BY_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        // verifies that a certain behavior happened n number of times
        // verify(mock, times(1)).someMethod("some arg");
        // we use argument matchers here, but the dude in the course said
        // that we will be using argument captors later
        verify(beerService).replaceById(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void testCreateNewBeer() throws Exception {

        BeerDTO newBeer = beerServiceImpl.listBeers().get(0);
        newBeer.setVersion(null);
        newBeer.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.PATH_ALL_BEERS)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBeer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void listBeers() throws Exception {

        List<BeerDTO> beerList = beerServiceImpl.listBeers();

        given(beerService.listBeers()).willReturn(beerList);

        mockMvc.perform(get(BeerController.PATH_ALL_BEERS).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(5)));

    }

    @Test
    void getBeerByID() throws Exception {

        // getting data for stubbing
        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

        // stubbing mocked service
        given(beerService.beerExists(any(UUID.class))).willReturn(true);
        given(beerService.getBeerByID(testBeer.getId())).willReturn(testBeer);

        // building request, performing it and performing expectations
        // (similar to assertions) with matchers
        mockMvc.perform(get(BeerController.PATH_BEER_BY_ID, testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));

    }

}
