package com.example.springbootgraphqlmysql.repository;

import com.example.springbootgraphqlmysql.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByEmail(String email);
}
