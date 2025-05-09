package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Customer;
import com.spring.spring_6_rest_mvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(path = "{customerID}", method = RequestMethod.GET)
    public Customer getCustomerByID(@PathVariable("customerID") UUID id) {
        return customerService.getCustomerByID(id);
    }


}
