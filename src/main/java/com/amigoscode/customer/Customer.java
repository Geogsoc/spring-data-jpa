package com.amigoscode.customer;


import com.amigoscode.book.LibraryBook;
import com.amigoscode.course.Course;
import com.amigoscode.customeridcard.CustomerIdCard;
import com.amigoscode.enrollment.CourseEnrollment;
import com.github.javafaker.DateAndTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Setter
@Getter
@AllArgsConstructor
//@ToString(exclude = "libraryBooks, customerIdCard, courseEnrollments")
@SQLDelete(sql = "UPDATE customer SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@EntityListeners({AuditingEntityListener.class})
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
            generator = "customer_sequence")
    private Long id;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = false)
    private CustomerIdCard customerIdCard;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(columnDefinition = "TEXT")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    private String modifiedBy;

    @CreatedBy
    private String createdBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @LastModifiedDate
    @Column(updatable = false)
    private Instant modifiedAt;

    @Column(nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
//,fetch = FetchType.EAGER
    private Set<LibraryBook> libraryBooks = new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "customer",orphanRemoval = true)
    private Set<CourseEnrollment> courseEnrollments = new HashSet<>();


    private ZonedDateTime deletedAt;

    public void addCourseEnrollment(Course course) {
        courseEnrollments.add(new CourseEnrollment(this, course));
    }

    public void removeCourseEnrollment(Course course) {

        courseEnrollments.removeIf(courseEnrollment -> courseEnrollment.getCourse().equals(course));


    }

    public Customer(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public void addBooks(LibraryBook book) {

        if (!libraryBooks.contains(book)) {
            libraryBooks.add(book);
            book.setCustomer(this);
        }

    }

    public void removeBook(LibraryBook book) {

        if (libraryBooks.contains(book)) {
            libraryBooks.remove(book);
            book.setCustomer(null);
        }

    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }


}
