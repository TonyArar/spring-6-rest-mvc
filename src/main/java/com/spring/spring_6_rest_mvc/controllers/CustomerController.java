package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Customer;
import com.spring.spring_6_rest_mvc.services.CustomerService;
import com.spring.spring_6_rest_mvc.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class CustomerController {

    // URIs/paths and co.
    public static final String PATH_ALL_CUSTOMERS = "/api/v1/customers";
    public static final String PATHVAR_CUSTOMER_ID = "customerID";
    // "/api/v1/customers/{customerID}"
    public static final String PATH_CUSTOMER_BY_ID =
            PATH_ALL_CUSTOMERS + "/{" + PATHVAR_CUSTOMER_ID + "}";

    private final CustomerService customerService;

    @PatchMapping(PATH_CUSTOMER_BY_ID)
    public ResponseEntity updateCustomerById(@PathVariable(PATHVAR_CUSTOMER_ID) UUID customerToBeUpdatedId,@RequestBody Customer customerPatch){
        customerService.updateCustomerById(customerToBeUpdatedId, customerPatch);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATH_CUSTOMER_BY_ID)
    public ResponseEntity removeCustomerById(@PathVariable(PATHVAR_CUSTOMER_ID) UUID customerToBeRemovedId){
        customerService.removeById(customerToBeRemovedId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(PATH_CUSTOMER_BY_ID)
    public ResponseEntity replaceCustomerById(@PathVariable(PATHVAR_CUSTOMER_ID) UUID customerToBeReplacedID, @RequestBody Customer newCustomer){
        customerService.replaceCustomerById(customerToBeReplacedID, newCustomer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(PATH_ALL_CUSTOMERS)
    public ResponseEntity createCustomer(@RequestBody Customer customer){

        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customers/" + savedCustomer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(PATH_ALL_CUSTOMERS)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(PATH_CUSTOMER_BY_ID)
    public Customer getCustomerByID(@PathVariable(PATHVAR_CUSTOMER_ID) UUID id) {
        return customerService.getCustomerByID(id).orElseThrow(ResourceNotFoundException::new);
    }


}
