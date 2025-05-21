package com.ccsw.tutorial.customer;

import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            Boolean existCustomer = this.customerRepository.existsByName(dto.getName().trim());

            if (!existCustomer) {
                customer.setName(dto.getName());
                this.customerRepository.save(customer);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().body(existCustomer);
            }

    }

    @Override
    public void delete(Long id) throws Exception {

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
