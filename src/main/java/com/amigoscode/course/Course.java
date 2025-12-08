package com.amigoscode.course;


import com.amigoscode.customer.Customer;
import com.amigoscode.enrollment.CourseEnrollment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Course {

    @Id
    @SequenceGenerator(name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,
            generator = "course_sequence")
    private Long id;

    @Column(columnDefinition = "TEXT",
            nullable = false,
            unique = true)
    private String name;

    @Column(columnDefinition = "TEXT",
            nullable = false)
    private String department;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "course",orphanRemoval = true)
    private Set<CourseEnrollment> courseEnrollments = new HashSet<>();

    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public void addCourseEnrollment(Customer customer) {
        courseEnrollments.add(new CourseEnrollment(customer, this));
    }

    public void removeCourseEnrollment(Customer customer) {
        courseEnrollments.removeIf(courseEnrollment -> courseEnrollment.getCustomer().equals(customer));
    }

    @Override
    public String toString() {
        return name + " (" + department + ")";
    }
}
