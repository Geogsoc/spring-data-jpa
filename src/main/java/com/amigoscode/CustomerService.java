package com.amigoscode;

import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Optional<Customer> getCustomerWithBooks(Long customerId) {

        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return Optional.empty();
        }

        Hibernate.initialize(customer.get().getLibraryBooks());
        return customer;
    }
}
