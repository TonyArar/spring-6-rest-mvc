package com.spring.spring_6_rest_mvc.repositories;

import com.spring.spring_6_rest_mvc.entities.Customer;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomerConstraintViolation(){
        assertThrows(ConstraintViolationException.class, () -> {
            Customer customerToBeSaved = Customer
                    .builder()
                    .customerName("This String is longer than 50 chars: 123456789101112")
                    .build();
            Customer savedCustomer = customerRepository.save(customerToBeSaved);
            customerRepository.flush();
        });
    }

    @Test
    void testSaveCustomer(){
        Customer customerToBeSaved = Customer
                .builder()
                .customerName("My Customer")
                .build();
        Customer savedCustomer = customerRepository.save(customerToBeSaved);
        customerRepository.flush();
        assertNotNull(savedCustomer);
        assertEquals("My Customer", savedCustomer.getCustomerName());
        assertNotNull(customerToBeSaved.getId());
    }

}
