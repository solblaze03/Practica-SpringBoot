package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public Customer get(Long id) {
        return this.customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return this.customerRepository.findAllByOrderByIdAsc();
    }

    @Override
    public ResponseEntity<?> save(Long id, CustomerDto dto) {

        Customer customer;
        if(id == null){
            customer = new Customer();
        }else{
            customer = this.get(id);
        }



        customer.setName(dto.getName());

        Customer ExistCustomer = this.customerRepository.findByName(customer.getName());

        if(ExistCustomer != null){
            return ResponseEntity.badRequest().build();
        }else{
            System.out.println(false);
        }

        this.customerRepository.save(customer);
        return ResponseEntity.ok().build();
    }

    @Override
    public void delete(Long id) throws Exception {
        System.out.println("------> "+ id+ " -- "+this.get(id).getName());
        if(this.get(id) == null ){
            throw new Exception("NotExists");
        }

        this.customerRepository.deleteById(id);
    }

    @Override
    public Customer findByName(String name) {
        return this.customerRepository.findByName(name);
    }


}
