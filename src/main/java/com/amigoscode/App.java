package com.amigoscode;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);

    }

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                        CustomerIdCardRepository customerIdCardRepository,
                                        LibraryBookRepository libraryBookRepository) {

        return args -> {

            Customer customer1 = new Customer("Jeff", "Banks", "jeff.banks@gmail.com", 33);

            LibraryBook warAndPeace = new LibraryBook();
            warAndPeace.setTitle("War and Peace");
            warAndPeace.setCustomer(customer1);
//            LibraryBook hitchhikersGuide = new LibraryBook();
//            hitchhikersGuide.setTitle("The Hitchhiker's Guide to the Galaxy");


            customer1.setLibraryBooks(Set.of(warAndPeace));
// now save the customer
            customerRepository.save(customer1);

            //            hitchhikersGuide.setCustomer(customer1);

// add books to the customer's libraryBooks set
            libraryBookRepository.findAll().forEach(libraryBook -> {
                System.out.println(" here is book " + libraryBook.getTitle());
                System.out.println(" here is customer " + libraryBook.getCustomer());
            });
            System.out.println("elis book ");

            customerRepository.findAll().forEach(customer -> {
                System.out.println(customer.getFirstName());
                customer.getLibraryBooks().forEach(book -> System.out.println(book.getTitle()));
            });

        };
    }

    private static void example3(CustomerRepository customerRepository) {
        CustomerIdCard customerIdCard = new CustomerIdCard();
        customerIdCard.setCardNumber("12345");

        Customer customer = new Customer("Elis", "Porter", "elis@gmail.com", 44);
        customer.setCustomerIdCard(customerIdCard);
        customerIdCard.setCustomer(customer);

        Customer customer2 = customerRepository.save(customer);


        System.out.println(customerRepository.findById(1L).get().getCustomerIdCard());

        customerRepository.deleteById(1L);
    }

    private static void example1(CustomerRepository customerRepository,
                                 CustomerIdCardRepository customerIdCardRepository,
                                 LibraryBookRepository libraryBookRepository) {

        Customer customer = new Customer("Jeff", "Wilkinson", "jeff@gmail.com", 33);
        customerRepository.save(customer);
        CustomerIdCard customerIdCard = new CustomerIdCard();
        customerIdCard.setCardNumber("12345");
        customerIdCard.setCustomer(customer);
        customerIdCardRepository.save(customerIdCard);

        Optional<CustomerIdCard> foundCard = customerIdCardRepository.findById(1L);

        System.out.println("---------------");

        System.out.println(customerIdCardRepository.findCustomerIdCardByIdWithCustomerIdCard(1L).get().getCustomer());
    }

    private static Sort sortAgeName() {

        return Sort.by("firstName").ascending().and(Sort.by("age").ascending());
    }

    private void generateCustomers(CustomerRepository customerRepository) {

        Faker faker = new Faker();
        for (int i = 0; i < 2; i++) {

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = "%s.%s@gmail.com".formatted(firstName, lastName);

            Customer customer = new Customer(firstName, lastName, email, faker.number().numberBetween(18, 130));

            customerRepository.save(customer);

        }
    }
}
