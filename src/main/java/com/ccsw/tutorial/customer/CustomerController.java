package com.ccsw.tutorial.customer;


import com.ccsw.tutorial.customer.model.Customer;
import com.ccsw.tutorial.customer.model.CustomerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Client", description = "API of client")
@RequestMapping(value = "/customer")
@RestController
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "Find", description = "Method that return a list of customers")
    @GetMapping(path = "")
    public List<CustomerDto> findAll() {
        List<Customer> categories = this.customerService.findAll();

        return categories.stream().map(e -> modelMapper.map(e, CustomerDto.class)).collect(Collectors.toList());

    }

    @Operation(summary = "Save or update", description = "Method that saves or updates a customers")
    @PutMapping(path = {"", "/{id}"})
    public ResponseEntity<?> save(@PathVariable(name = "id", required = false) Long id, @RequestBody CustomerDto dto) {


        return this.customerService.save(id, dto);


    }

    @Operation(summary = "Delete", description = "Method that deletes a customers")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) throws Exception {
        this.customerService.delete(id);
    }


}
