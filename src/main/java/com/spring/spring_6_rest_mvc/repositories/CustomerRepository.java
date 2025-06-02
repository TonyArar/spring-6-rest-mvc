package com.spring.spring_6_rest_mvc.repositories;

import com.spring.spring_6_rest_mvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
