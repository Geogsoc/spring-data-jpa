package com.amigoscode;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Customer {

    @Id
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private Integer age;

    public Customer(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

}
