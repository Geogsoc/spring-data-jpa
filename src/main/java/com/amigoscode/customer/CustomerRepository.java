package com.amigoscode.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //can just use query derived by method name eg  Optional<Customer> findCustomerByEmail(String email)
    //but best not to rely
    @Query("SELECT c FROM Customer c WHERE c.email =?1")
    Optional<Customer> findCustomerByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.firstName = :name  AND c.age > :age")
    List<Customer> findByFirstNameAndAgeGreaterThan(@Param("name") String firstName, @Param("age") int age);

    @Query(
            value = "SELECT * FROM customer WHERE first_name = :name AND age > :age",
            nativeQuery = true)
    List<Customer> findByFirstNameAndAgeGreaterThanNative(
            @Param("name") String firstName,
            @Param("age") int age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Customer c WHERE c.email = ?1")
    int deleteCustomerByEmail(String email);


    @Query("SELECT s from Customer s JOIN FETCH s.libraryBooks")
    List<Customer> selectCustomerWithLibraryBooks();

    @Query("SELECT s from Customer s JOIN FETCH s.libraryBooks WHERE s.id = ?1")
    Optional<Customer> findCustomerByIdWithBooks(Long customerId);
}
