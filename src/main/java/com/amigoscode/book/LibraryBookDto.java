package com.amigoscode.book;

import java.time.ZonedDateTime;

public record LibraryBookDto(Long id, String title, ZonedDateTime createdAt) {
}
