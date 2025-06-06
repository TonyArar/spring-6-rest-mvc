package com.spring.spring_6_rest_mvc.services;

import ch.qos.logback.core.util.StringUtil;
import com.spring.spring_6_rest_mvc.entities.Customer;
import com.spring.spring_6_rest_mvc.mappers.CustomerMapper;
import com.spring.spring_6_rest_mvc.models.CustomerDTO;
import com.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

@NoArgsConstructor
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> listCustomersByQueryParams(String customerName) {
        List<Customer> repoResult = customerRepository.findAllByQueryParams(customerName);
        return repoResult.stream().map(customerMapper::customerToCustomerDTO).toList();
    }

    @Override
    public CustomerDTO getCustomerByID(UUID id) {
        return customerMapper.customerToCustomerDTO(customerRepository.getReferenceById(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        Customer customerToBeSaved = Customer.builder()
                .customerName(customer.getCustomerName())
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        Customer savedCustomer = customerRepository.save(customerToBeSaved);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }

    @Override
    public void replaceCustomerById(UUID customerToBeReplacedID, CustomerDTO newCustomer) {

        Customer existingCustomer = customerRepository.getReferenceById(customerToBeReplacedID);

        existingCustomer.setCustomerName(newCustomer.getCustomerName());
        existingCustomer.setCreateDate(LocalDateTime.now());
        existingCustomer.setLastModifiedDate(LocalDateTime.now());

        customerRepository.save(existingCustomer);

    }

    @Override
    public void removeById(UUID customerToBeRemovedId) {
        customerRepository.deleteById(customerToBeRemovedId);
    }

    @Override
    public void updateCustomerById(UUID customerToBeUpdatedId, CustomerDTO customerPatch) {

        Customer customerToBeUpdated = customerRepository.getReferenceById(customerToBeUpdatedId);

        String newName = customerPatch.getCustomerName();

        Predicate<String> hasText = StringUtil::notNullNorEmpty;

        if (hasText.test(newName)) {
            customerToBeUpdated.setCustomerName(newName);
        }

        customerRepository.save(customerToBeUpdated);

    }

    @Override
    public boolean customerExists(UUID customerId) {
        return customerRepository.existsById(customerId);
    }


}
