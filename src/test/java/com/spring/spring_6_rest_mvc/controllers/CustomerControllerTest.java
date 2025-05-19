package com.spring.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.spring_6_rest_mvc.models.Customer;
import com.spring.spring_6_rest_mvc.services.CustomerService;
import com.spring.spring_6_rest_mvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    // data source
    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testUpdateCustomer() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(patch("/api/v1/customers/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objectMapper)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class), any(Customer.class));

    }

    @Test
    void testCreateNewCustomer() throws Exception {

        // create new data
        Customer newCustomer = customerServiceImpl.listCustomers().get(0);
        newCustomer.setVersion(null);
        newCustomer.setId(null);

        // stub mocked service
        given(customerService.saveNewCustomer(any(Customer.class))).willReturn(newCustomer);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        mockMvc.perform(post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

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