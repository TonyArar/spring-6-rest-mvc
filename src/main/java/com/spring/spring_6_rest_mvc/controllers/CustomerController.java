package com.spring.spring_6_rest_mvc.controllers;

import com.spring.spring_6_rest_mvc.models.Customer;
import com.spring.spring_6_rest_mvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @DeleteMapping("{customerToBeRemovedId}")
    public ResponseEntity removeCustomerById(@PathVariable("customerToBeRemovedId") UUID customerToBeRemovedId){
        customerService.removeById(customerToBeRemovedId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "{customerToBeReplacedID}")
    public ResponseEntity replaceCustomerById(@PathVariable("customerToBeReplacedID") UUID customerToBeReplacedID, @RequestBody Customer newCustomer){
        customerService.replaceCustomerById(customerToBeReplacedID, newCustomer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Customer customer){

        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "api/v1/customers/" + savedCustomer.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(path = "{customerID}", method = RequestMethod.GET)
    public Customer getCustomerByID(@PathVariable("customerID") UUID id) {
        return customerService.getCustomerByID(id);
    }


}
