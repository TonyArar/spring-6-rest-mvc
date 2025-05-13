package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.services.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;




import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BeerController.class})
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BeerService beerService;

    @Test
    void getBeerByID() throws Exception {

        mockMvc.perform(get("/api/v1/beers/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());


    }

}