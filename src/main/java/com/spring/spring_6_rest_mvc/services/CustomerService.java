package com.spring.spring_6_rest_mvc.services;

import com.spring.spring_6_rest_mvc.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();

    Customer getCustomerByID(UUID id);

    Customer saveNewCustomer(Customer customer);
}
