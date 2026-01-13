package com.amigoscode.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {

    @Query("SELECT new com.amigoscode.book.LibraryBookDto(b.id, b.title, b.createdAt) FROM LibraryBook b")
    List<LibraryBookDto> getAllBooksDto();

    @Query("SELECT new com.amigoscode.book.LibraryBookDto(b.id, b.title, b.createdAt) FROM LibraryBook b WHERE b.id = ?1")
    List<LibraryBookDto> getBookDtoById();
}
