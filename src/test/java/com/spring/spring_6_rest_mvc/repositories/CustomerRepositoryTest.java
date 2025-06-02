package com.spring.spring_6_rest_mvc.repositories;

import com.spring.spring_6_rest_mvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer(){
        Customer customerToBeSaved = Customer
                .builder()
                .customerName("My Customer")
                .build();
        Customer savedCustomer = customerRepository.save(customerToBeSaved);
        assertNotNull(savedCustomer);
        assertEquals("My Customer", savedCustomer.getCustomerName());
        assertNotNull(customerToBeSaved.getId());
    }

}
