package com.spring.spring_6_rest_mvc.repositories;

import com.spring.spring_6_rest_mvc.entities.Beer;
import com.spring.spring_6_rest_mvc.models.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

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
                .upc("012345678910")
                .beerStyle(BeerStyle.PILSNER)
                .price(BigDecimal.valueOf(777))
                .quantityOnHand(3)
                .build();
        Beer savedBeer = beerRepository.save(beerToBeSaved);
        // from flush() docs: flushes all pending changes to the database
        // call flush so that this won't happen:
        // the test will pass on invalid beer fields if we don't call flush()
        // after saving the beer, because saving is batch-processed and test
        // finishes execution before saving is processed, so validation exception
        // flies under the test's radar unnoticed and the test passes
        // (evil and silent semantically incorrect behaviour)
        beerRepository.flush();
        assertNotNull(savedBeer);
        assertEquals("My Beer", savedBeer.getBeerName());
        assertNotNull(savedBeer.getId());
    }

}
