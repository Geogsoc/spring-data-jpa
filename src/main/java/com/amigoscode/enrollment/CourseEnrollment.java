package com.amigoscode.enrollment;

import com.amigoscode.course.Course;
import com.amigoscode.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollment {
    @EmbeddedId
    private EnrollmentId enrollmentId;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(
                    name = "enrollment_customer_id_fk"
            )
    )
    @MapsId("customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "course_id",
            foreignKey = @ForeignKey(
                    name = "enrollment_course_id_fk"
            )
    )
    @MapsId("courseId")
    private Course course;

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @PrePersist
    void prePersist() {
        createdAt = ZonedDateTime.now();
    }

    public CourseEnrollment(Customer customer, Course course) {
        this.customer = customer;
        this.course = course;
        this.enrollmentId = new EnrollmentId(customer.getId(), course.getId());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseEnrollment courseEnrollment)) return false;
        return Objects.equals(enrollmentId, courseEnrollment.enrollmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId);
    }

//    public CourseEnrollment(Course course, Customer customer) {
//        this.customer = customer;
//        this.course = course;
//        this.enrollmentId = new EnrollmentId(customer.getId(), course.getId());
//
//    }

}
