package com.spring.spring_6_rest_mvc.services;

import com.spring.spring_6_rest_mvc.dtos.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerByID(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    void replaceCustomerById(UUID customerToBeReplacedID, CustomerDTO newCustomer);

    void removeById(UUID customerToBeRemovedId);

    void updateCustomerById(UUID customerToBeUpdatedId, CustomerDTO customerPatch);
}
