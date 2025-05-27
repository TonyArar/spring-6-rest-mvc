package com.spring.spring_6_rest_mvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.spring_6_rest_mvc.exceptions.ResourceNotFoundException;
import com.spring.spring_6_rest_mvc.models.Customer;
import com.spring.spring_6_rest_mvc.services.CustomerService;
import com.spring.spring_6_rest_mvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;


import java.util.*;

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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void getCustomerByIDBeerNotFound() throws Exception {

        given(customerService.getCustomerByID(any(UUID.class))).willThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.PATH_CUSTOMER_BY_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void testUpdateCustomer() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().get(0);

        Map<String, Object> customerPatch = new HashMap<>();
        customerPatch.put("customerName", "TEST NAME");

        mockMvc.perform(patch(CustomerController.PATH_CUSTOMER_BY_ID, customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerPatch)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertEquals(customer.getId(), uuidArgumentCaptor.getValue());
        assertEquals(customerPatch.get("customerName"), customerArgumentCaptor.getValue().getCustomerName());

    }

    @Test
    void testRemoveCustomer() throws Exception{

        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete(CustomerController.PATH_CUSTOMER_BY_ID, customer.getId()))
                .andExpect(status().isNoContent());

        verify(customerService).removeById(uuidArgumentCaptor.capture());

        assertEquals(customer.getId(), uuidArgumentCaptor.getValue());

    }

    @Test
    void testReplaceCustomer() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put(CustomerController.PATH_CUSTOMER_BY_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objectMapper)))
                .andExpect(status().isNoContent());

        verify(customerService).replaceCustomerById(any(UUID.class), any(Customer.class));

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

        mockMvc.perform(post(CustomerController.PATH_ALL_CUSTOMERS)
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
        mockMvc.perform(get(CustomerController.PATH_ALL_CUSTOMERS)
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
        mockMvc.perform(get(CustomerController.PATH_CUSTOMER_BY_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));

    }


}