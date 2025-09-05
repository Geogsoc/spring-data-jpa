package com.amigoscode;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

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

            generateCustomers(customerRepository);

            System.out.println(customerRepository.count());

            Sort sort = Sort.by( "firstName").ascending();

            customerRepository.findAll(sort).forEach(customer -> {
                System.out.println(customer.getFirstName());

            });
        };

    }

    private void generateCustomers(CustomerRepository customerRepository) {

        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = "%s.%s@gmail.com".formatted(firstName, lastName);

            Customer customer = new Customer(firstName, lastName, email, faker.number().numberBetween(18, 130));

            customerRepository.save(customer);

        }
    }
}
