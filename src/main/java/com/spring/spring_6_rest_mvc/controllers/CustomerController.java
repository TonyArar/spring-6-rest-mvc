package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.exception_handling.ResourceNotFoundException;
import com.spring.spring_6_rest_mvc.models.CustomerDTO;
import com.spring.spring_6_rest_mvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {


    // URIs/paths and co.

    public static final String PATH_ALL_CUSTOMERS = "/api/v1/customers";
    public static final String PATHVAR_CUSTOMER_ID = "customerID";

    // "/api/v1/customers/{customerID}"
    public static final String PATH_CUSTOMER_BY_ID =
            PATH_ALL_CUSTOMERS + "/{" + PATHVAR_CUSTOMER_ID + "}";


    // Dependencies

    private final CustomerService customerService;


    // REST API Endpoints

    @PatchMapping(PATH_CUSTOMER_BY_ID)
    public ResponseEntity updateCustomerById(@PathVariable(PATHVAR_CUSTOMER_ID) UUID customerToBeUpdatedId, @RequestBody CustomerDTO customerPatch){
        if (!customerService.customerExists(customerToBeUpdatedId)) throw new ResourceNotFoundException();
        customerService.updateCustomerById(customerToBeUpdatedId, customerPatch);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PATH_CUSTOMER_BY_ID)
    public ResponseEntity removeCustomerById(@PathVariable(PATHVAR_CUSTOMER_ID) UUID customerToBeRemovedId){
        if (!customerService.customerExists(customerToBeRemovedId)) throw new ResourceNotFoundException();
        customerService.removeById(customerToBeRemovedId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(PATH_CUSTOMER_BY_ID)
    public ResponseEntity replaceCustomerById(@PathVariable(PATHVAR_CUSTOMER_ID) UUID customerToBeReplacedID,
                                              @Validated @RequestBody CustomerDTO newCustomer){
        if (!customerService.customerExists(customerToBeReplacedID)) throw new ResourceNotFoundException();
        customerService.replaceCustomerById(customerToBeReplacedID, newCustomer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(PATH_ALL_CUSTOMERS)
    public ResponseEntity createCustomer(@Validated @RequestBody CustomerDTO customer){
        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customers/" + savedCustomer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(PATH_ALL_CUSTOMERS)
    public List<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(PATH_CUSTOMER_BY_ID)
    public CustomerDTO getCustomerByID(@PathVariable(PATHVAR_CUSTOMER_ID) UUID id) {
        if (!customerService.customerExists(id)) throw new ResourceNotFoundException();
        return customerService.getCustomerByID(id);
    }


}
