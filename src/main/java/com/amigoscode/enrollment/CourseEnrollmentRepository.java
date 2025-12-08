package com.amigoscode.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, EnrollmentId> {

}
