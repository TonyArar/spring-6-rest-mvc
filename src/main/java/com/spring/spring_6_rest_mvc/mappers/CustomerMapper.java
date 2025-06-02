package com.spring.spring_6_rest_mvc.mappers;

import com.spring.spring_6_rest_mvc.entities.Customer;
import com.spring.spring_6_rest_mvc.models.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);

}
