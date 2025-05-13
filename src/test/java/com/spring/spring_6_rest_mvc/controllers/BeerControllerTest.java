package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.services.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

// MockMvcRequestBuilders
// imports all static methods (request builders) that enable us to build mock requests to use with Mock-MVC
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// MockMvcResultMatchers
// these methods match the result of an executed request against some expectation
// they serve like assertions but for Mock-MVC
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @SpringBootTest

// spring test slice, a test slice restricts the type of beans that are allowed to be injected by spring
// the @WebMvcTest test slice restricts this to only MVC-Controllers!
// parameters controllers = {classes} specifies which controllers should be autowired (injected)
// we can however inject other types of objects that are not beans, such as a MockMvc object
@WebMvcTest(controllers = {BeerController.class})
class BeerControllerTest {

    // no longer need to autowire, since @WebMvcTest provides it for us
    //  through param controllers = {BeerController.class}
    // @Autowired
    // BeerController beerController;

    // we need to autowire this:
    // Mock-MVC object that provides the mocked dispatcher servlet
    // that in turn contains a WebApplicationContext that contain mocked beans
    // it also allows us to perform requests mocked with request builders
    // @Autowired tells spring to create this for us
    @Autowired
    MockMvc mockMvc;

    // mock service (controller dependency) with mockito
    // @MockBean (deprecated)
    @MockitoBean
    BeerService beerService;

    @Test
    void getBeerByID() throws Exception {

        // sanity test
        // assertNotNull(mockMvc.getDispatcherServlet().getWebApplicationContext().getBean(BeerController.class));

        // perform request using MockMvc object and MockMvcRequestBuilders static methods
        // and match its result (test/assert/check) them using MockMvcResultMatchers static methods
        // get(uri) invokes corresponding endpoint method in BeerController bean
        mockMvc.perform(get("/api/v1/beers/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        // System.out.println(beerController.getBeerByID(UUID.randomUUID()));

    }

}