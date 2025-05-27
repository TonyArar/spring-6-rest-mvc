package com.spring.spring_6_rest_mvc.services;

import com.spring.spring_6_rest_mvc.models.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();

    Optional<Customer> getCustomerByID(UUID id);

    Customer saveNewCustomer(Customer customer);

    void replaceCustomerById(UUID customerToBeReplacedID, Customer newCustomer);

    void removeById(UUID customerToBeRemovedId);

    void updateCustomerById(UUID customerToBeUpdatedId, Customer customerPatch);
}
