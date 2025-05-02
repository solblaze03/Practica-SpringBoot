package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {

    Customer get(Long id);

    List<Customer> findAll();

    ResponseEntity<?> save(Long id , CustomerDto client);

    void delete(Long id) throws Exception;

    Boolean existByName(String name);
}
