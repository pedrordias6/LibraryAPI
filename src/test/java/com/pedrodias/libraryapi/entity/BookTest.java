package com.pedrodias.libraryapi.entity;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class BookTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateBookUsingBuilderAndExposeValues() {
        LocalDateTime now = LocalDateTime.of(2026, 7, 6, 10, 0);

        Book book = Book.builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .isbn("9780132350884")
                .publicationYear(2008)
                .genre("Programming")
                .available(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertThat(book.getTitle()).isEqualTo("Clean Code");
        assertThat(book.getAuthor()).isEqualTo("Robert C. Martin");
        assertThat(book.getIsbn()).isEqualTo("9780132350884");
        assertThat(book.getPublicationYear()).isEqualTo(2008);
        assertThat(book.getGenre()).isEqualTo("Programming");
        assertThat(book.getAvailable()).isTrue();
        assertThat(book.getCreatedAt()).isEqualTo(now);
        assertThat(book.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldFailValidationWhenRequiredFieldsAreMissing() {
        Book book = Book.builder()
                .title(" ")
                .author("")
                .isbn(" ")
                .publicationYear(null)
                .available(null)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertThat(violations)
                .extracting(ConstraintViolation::getPropertyPath)
                .extracting(Object::toString)
                .contains("title", "author", "isbn", "publicationYear", "available");
    }

    @Test
    void shouldPassValidationForCompleteBook() {
        LocalDateTime now = LocalDateTime.of(2026, 7, 6, 10, 0);
        Book book = Book.builder()
                .title("Effective Java")
                .author("Joshua Bloch")
                .isbn("9780134685991")
                .publicationYear(2018)
                .genre("Programming")
                .available(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertThat(violations).isEmpty();
    }
}
