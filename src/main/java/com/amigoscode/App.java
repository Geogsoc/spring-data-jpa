package com.amigoscode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class App {

    public static void main(String[] args) {


        SpringApplication.run(App.class, args);


    }


    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {


        return args -> {

            Customer customer = new Customer("Elis", "Porter", "elis@gmail.com", 44);
            Customer customer2 = new Customer("Bob", "Porter", "eli4s@gmail", 44);
            customerRepository.save(customer);
            customerRepository.save(customer2);

            System.out.println(customerRepository.count());

            System.out.println(customerRepository.findAll());

            System.out.println(" System.out.println(customerRepository.existsById(1L))");
            System.out.println(customerRepository.existsById(1L));

            Optional<Customer> test = customerRepository.findCustomerByEmail("elis@gmail.com");
            System.out.println(test.get().toString());

            int isItANumber = customerRepository.deleteCustomerByEmail("elis@gmail.com");

            System.out.println(isItANumber);

            List<Customer> test2 = customerRepository.findByFirstNameAndAgeGreaterThan("Elis", 0);
            System.out.println(test.get().toString());
            System.out.println(test2.toString());

        };
    }
}
