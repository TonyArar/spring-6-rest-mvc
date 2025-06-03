package com.spring.spring_6_rest_mvc.bootstrap_data;

import com.spring.spring_6_rest_mvc.repositories.BeerRepository;
import com.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DatabasePopulateTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    DatabasePopulate databasePopulate;

    @BeforeEach
    void setUP() {
        if (databasePopulate == null){
            databasePopulate = new DatabasePopulate(beerRepository, customerRepository);
        }
    }

    @Test
    void run() throws Exception {
        databasePopulate.run(null);
        // make sure db is populated
        assertEquals(beerRepository.count(), databasePopulate.numberOfGeneratedBeers);
        assertEquals(customerRepository.count(), databasePopulate.numberOfGeneratedCustomers);
    }


}