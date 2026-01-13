package com.amigoscode.customer;

import com.amigoscode.book.LibraryBook;
import com.amigoscode.enrollment.CourseEnrollment;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record CustomerDto(Long id,
                          String firstName,
                          String LastName,
                          Set<LibraryBook> libraryBooks,
                          Set<CourseEnrollment> courseEnrollments,
                          Instant createdAt) {



}
