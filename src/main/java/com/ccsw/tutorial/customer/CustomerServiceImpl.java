package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
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
            customer.setName(dto.getName());
        }else{
            customer =  this.get(id);

        }
            Boolean ExistCustomer = this.customerRepository.existsByName(dto.getName());

            if (!ExistCustomer) {
                customer.setName(dto.getName());
                this.customerRepository.save(customer);
                return ResponseEntity.ok(ExistCustomer);
            } else {
                return ResponseEntity.badRequest().body(ExistCustomer);
            }

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
    public Boolean existByName(String name) {
        return this.customerRepository.existsByName(name);
    }


}
