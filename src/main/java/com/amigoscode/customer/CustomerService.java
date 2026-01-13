package com.amigoscode.customer;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream().
                map(c -> new CustomerDto(c.getId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getLibraryBooks(),
                        c.getCourseEnrollments(),
                        c.getCreatedAt())).toList();
    }

//    @Transactional
//    public Optional<Customer> getCustomerWithCourses(Long customerId) {
//
//        Optional<Customer> customer = customerRepository.findById(customerId);
//
//        if (customer.isEmpty()) {
//            return Optional.empty();
//        }
//
//        Hibernate.initialize(customer.get().getCourses());
//        return customer;
//    }
}
