package com.spring.spring_6_rest_mvc.bootstrap_data;

import com.github.javafaker.Faker;
import com.spring.spring_6_rest_mvc.entities.Beer;
import com.spring.spring_6_rest_mvc.entities.Customer;
import com.spring.spring_6_rest_mvc.models.BeerStyle;
import com.spring.spring_6_rest_mvc.repositories.BeerRepository;
import com.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class DatabasePopulate implements CommandLineRunner {

    public int numberOfGeneratedCustomers = 5;
    public int numberOfGeneratedBeers = 5;

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public DatabasePopulate(BeerRepository beerRepository, CustomerRepository customerRepository) {
        this.beerRepository = beerRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        populateCustomers();
        populateBeers();
    }

    private void populateCustomers() {
        Faker faker = new Faker();
        for (int i = 1; i <= numberOfGeneratedCustomers; i++){
            Customer customer = Customer.builder()
                    .customerName(faker.funnyName().name())
                    .createDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            this.customerRepository.save(customer);
        }
    }

    private void populateBeers() {
        Faker faker = new Faker();
        for (int i = 1; i <= numberOfGeneratedBeers; i++){
            Random random = new Random();
            Beer beer = Beer.builder()
                    .beerName(faker.beer().name())
                    .beerStyle(BeerStyle.values()[random.nextInt(0, 10)])
                    .upc(faker.code().gtin13().substring(1))
                    .quantityOnHand(random.nextInt(0, 101))
                    .price(BigDecimal.valueOf(random.nextDouble() * 100))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            this.beerRepository.save(beer);
        }
    }

}
