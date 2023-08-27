package com.example.springbootgraphqlmysql.controller;

import com.example.springbootgraphqlmysql.dto.CustomerInput;
import com.example.springbootgraphqlmysql.model.Customer;
import com.example.springbootgraphqlmysql.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@Slf4j
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @QueryMapping(name = "customers") // for custom mapping from schema graphql
    public Iterable<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @QueryMapping
    public Customer customerById(@Argument Long id){
        return customerRepository.findById(id).orElseThrow();
    }

    @QueryMapping
    public Customer customerByEmail(@Argument String email){
        return customerRepository.findCustomerByEmail(email);
    }

    @MutationMapping
    public Customer deleteCustomerById(@Argument Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent(value -> customerRepository.deleteById(value.getId()));
        return customer.orElseThrow();
    }

    @MutationMapping
    public Customer addCustomer(@Argument(name = "input") CustomerInput request){
        Customer customer = new Customer();
        BeanUtils.copyProperties(request, customer);
        return customerRepository.save(customer);
    }
}
