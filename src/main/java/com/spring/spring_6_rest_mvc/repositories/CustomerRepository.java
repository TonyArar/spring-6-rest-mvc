package com.spring.spring_6_rest_mvc.repositories;

import com.spring.spring_6_rest_mvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {


    @Query(value = "SELECT c " +
            "FROM Customer c " +
            "WHERE ( :customerName IS NULL OR lower(c.customerName) LIKE lower(concat('%', ?1,'%')) )")
    List<Customer> findAllByQueryParams(String customerName);


}
