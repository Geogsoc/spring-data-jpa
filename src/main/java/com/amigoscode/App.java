package com.amigoscode;

import com.amigoscode.account.Account;
import com.amigoscode.account.AccountRepository;
import com.amigoscode.account.AccountService;
import com.amigoscode.book.LibraryBook;
import com.amigoscode.book.LibraryBookRepository;
import com.amigoscode.course.Course;
import com.amigoscode.course.CourseRepository;
import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.amigoscode.customer.CustomerService;
import com.amigoscode.customeridcard.CustomerIdCard;
import com.amigoscode.customeridcard.CustomerIdCardRepository;
import com.amigoscode.enrollment.CourseEnrollment;
import com.amigoscode.enrollment.CourseEnrollmentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);

    }

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                        CustomerIdCardRepository customerIdCardRepository,
                                        LibraryBookRepository libraryBookRepository,
                                        CustomerService customerService,
                                        CourseRepository courseRepository,
                                        CourseEnrollmentRepository courseEnrollmentRepository,
                                        AccountService accountService,
                                        AccountRepository accountRepository) {

        return args -> {

            Account current = new Account();
            current.setBalance(new BigDecimal("100"));

            accountRepository.save(current);

            Account savings = new Account();
            savings.setBalance(new BigDecimal("100"));

            accountRepository.save(savings);

            accountService.transfer(current,savings,new BigDecimal("10"));


            accountRepository.findAll().forEach(a-> System.out.println(a.getBalance()));

        };
    }

    private static void courseEnrollmentPractice(CustomerRepository customerRepository, CourseEnrollmentRepository courseEnrollmentRepository) {
        Customer jeff = new Customer("Jeff", "Banks", "jeff.banks@gmail.com", 33);
        Customer elis = new Customer("Elis", "Porter", "elis@gmail.com", 44);


        Course cScourse = new Course("Computer Science", "IT");

        Course aIcourse = new Course("AI", "IT");


        jeff.addCourseEnrollment(cScourse);
        jeff.addCourseEnrollment(aIcourse);
        elis.addCourseEnrollment(cScourse);

        customerRepository.saveAll(List.of(jeff, elis));


        courseEnrollmentRepository.findAll().forEach(courseEnrollment -> {

            System.out.println(courseEnrollment.getEnrollmentId());

            System.out.printf("%s  %s%n",
                    courseEnrollment.getCustomer().getFirstName(),
                    courseEnrollment.getCourse().getName()
            );

            System.out.println();
        });


        jeff.removeCourseEnrollment(cScourse);

        customerRepository.save(jeff);

        System.out.println("removing -> \n");


        courseEnrollmentRepository.findAll().forEach(courseEnrollment -> {

            System.out.println(courseEnrollment.getEnrollmentId());

            System.out.printf("%s  %s%n",
                    courseEnrollment.getCustomer().getFirstName(),
                    courseEnrollment.getCourse().getName()
            );

            System.out.println();
        });

        //   customerRepository.findAllWithCourses().forEach(customer -> System.out.println(customer.getCourses()));
    }

    private static void oneToManyExamples(CustomerRepository customerRepository, LibraryBookRepository libraryBookRepository) {
        Customer customer1 = new Customer("Jeff", "Banks", "jeff.banks@gmail.com", 33);

        LibraryBook warAndPeace = new LibraryBook();
        warAndPeace.setTitle("War and Peace");

        customer1.addBooks(warAndPeace);

        customerRepository.save(customer1);

        System.out.println(libraryBookRepository.count());

        customerRepository.selectCustomerWithLibraryBooks().forEach(customer -> {

            System.out.println(customer.getFirstName());
            System.out.println("Book size: " + customer.getLibraryBooks().size());

        });

        customer1.removeBook(warAndPeace);

        customerRepository.save(customer1);

        System.out.println(libraryBookRepository.count());

        customerRepository.selectCustomerWithLibraryBooks().forEach(customer -> {

                    System.out.println(customer.getFirstName());
                    System.out.println("Book size after removal : " + customer.getLibraryBooks().size());

                }
        );
    }

    private static void oneToMany(CustomerRepository customerRepository, LibraryBookRepository libraryBookRepository, CustomerService customerService) {
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

        customerService.getCustomerWithBooks(1L).ifPresent(c -> {
            System.out.println(c.getFirstName() + " < - > " + c.getLastName());
        });

// add books to the customer's libraryBooks set
        libraryBookRepository.findAll().forEach(libraryBook -> {
            System.out.println(" here is book " + libraryBook.getTitle());
            System.out.println(" here is customer " + libraryBook.getCustomer());
        });
        System.out.println("elis book ");

        customerRepository.selectCustomerWithLibraryBooks().forEach(customer -> {
            System.out.println(customer.getFirstName());

            customer.getLibraryBooks().forEach(book -> System.out.println(book.getTitle()));
        });
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
