package com.spring.spring_6_rest_mvc.services;

import com.github.javafaker.Faker;
import com.spring.spring_6_rest_mvc.models.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl(){

        this.customerMap = new HashMap<>();
        Faker faker = new Faker();

        int numberOfGeneratedCustomers = 5;

        for (int i = 1; i <= numberOfGeneratedCustomers; i++){
            Random random = new Random();
            Customer customer = Customer.builder()
                    .customerName(faker.funnyName().name())
                    .id(UUID.randomUUID())
                    .version(random.nextInt(1,5))
                    .createDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            this.customerMap.put(customer.getId(), customer);
        }

    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<Customer>(customerMap.values());
    }

    @Override
    public Customer getCustomerByID(UUID id) {
        return customerMap.get(id);
    }


    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer customerToBeSaved = Customer.builder()
                .customerName(customer.getCustomerName())
                .id(UUID.randomUUID())
                .version(1)
                .createDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        customerMap.put(customerToBeSaved.getId(), customerToBeSaved);
        return customerToBeSaved;
    }

    @Override
    public void replaceCustomerById(UUID customerToBeReplacedID, Customer newCustomer) {
        Customer existingCustomer = customerMap.get(customerToBeReplacedID);
        existingCustomer.setCustomerName(newCustomer.getCustomerName());
        existingCustomer.setCreateDate(LocalDateTime.now());
        existingCustomer.setLastModifiedDate(LocalDateTime.now());
    }


}
