package com.spring.spring_6_rest_mvc.services;

import com.spring.spring_6_rest_mvc.models.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    CustomerDTO getCustomerByID(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    void replaceCustomerById(UUID customerToBeReplacedID, CustomerDTO newCustomer);

    void removeById(UUID customerToBeRemovedId);

    void updateCustomerById(UUID customerToBeUpdatedId, CustomerDTO customerPatch);

    boolean customerExists(UUID customerId);

}
