package com.spring.spring_6_rest_mvc.repositories;

import com.spring.spring_6_rest_mvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

// From @DataJpaTest docs:
// Annotation for a JPA test that focuses only on JPA components.
// Using this annotation only enables auto-configuration that is
// relevant to Data JPA tests. Similarly, component scanning is
// limited to JPA repositories and entities (@Entity).
// read rest of docs ...
@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer(){
        Beer beerToBeSaved = Beer
                .builder()
                .beerName("My Beer")
                .build();
        Beer savedBeer = beerRepository.save(beerToBeSaved);
        assertNotNull(savedBeer);
        assertEquals("My Beer", savedBeer.getBeerName());
        assertNotNull(savedBeer.getId());
    }

}
