package com.spring.spring_6_rest_mvc.controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.spring.spring_6_rest_mvc.models.Customer;
import com.spring.spring_6_rest_mvc.services.CustomerService;
import com.spring.spring_6_rest_mvc.services.CustomerServiceImpl;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;


import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    // data source
    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    void listCustomers() throws Exception {

        // get data
        List<Customer> customerList = customerServiceImpl.listCustomers();

        // stub mocked CustomerService
        given(customerService.listCustomers()).willReturn(customerList);

        // build request, perform it and perform expectation on result
        // using MockMvc, Builders, Matchers and Actions
        mockMvc.perform(get("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(5)));


    }

    @Test
    void getCustomerByID() throws Exception {

        // get data
        Customer testCustomer = customerServiceImpl.listCustomers().get(0);

        // stub mocked CustomerService
        given(customerService.getCustomerByID(any(UUID.class))).willReturn(testCustomer);

        // build request, perform it and perform expectation on result
        // using MockMvc, Builders, Matchers and Actions
        mockMvc.perform(get("/api/v1/customers/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));

    }

}