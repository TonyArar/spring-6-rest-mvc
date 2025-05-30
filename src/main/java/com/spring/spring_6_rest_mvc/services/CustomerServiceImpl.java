package com.spring.spring_6_rest_mvc.services;

import ch.qos.logback.core.util.StringUtil;
import com.github.javafaker.Faker;
import com.spring.spring_6_rest_mvc.models.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl(){

        this.customerMap = new HashMap<>();
        Faker faker = new Faker();

        int numberOfGeneratedCustomers = 5;

        for (int i = 1; i <= numberOfGeneratedCustomers; i++){
            Random random = new Random();
            CustomerDTO customer = CustomerDTO.builder()
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
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<CustomerDTO>(customerMap.values());
    }

    @Override
    public CustomerDTO getCustomerByID(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO customerToBeSaved = CustomerDTO.builder()
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
    public void replaceCustomerById(UUID customerToBeReplacedID, CustomerDTO newCustomer) {
        CustomerDTO existingCustomer = customerMap.get(customerToBeReplacedID);
        existingCustomer.setCustomerName(newCustomer.getCustomerName());
        existingCustomer.setCreateDate(LocalDateTime.now());
        existingCustomer.setLastModifiedDate(LocalDateTime.now());
    }

    @Override
    public void removeById(UUID customerToBeRemovedId) {
        customerMap.remove(customerToBeRemovedId);
    }

    @Override
    public void updateCustomerById(UUID customerToBeUpdatedId, CustomerDTO customerPatch) {
        CustomerDTO customerToBeUpdated = customerMap.get(customerToBeUpdatedId);

        String newName = customerPatch.getCustomerName();

        Predicate<String> hasText = StringUtil::notNullNorEmpty;

        if (hasText.test(newName)) {
            customerToBeUpdated.setCustomerName(newName);
        }

    }

    @Override
    public boolean customerExists(UUID customerId) {
        return customerMap.get(customerId) != null;
    }


}
