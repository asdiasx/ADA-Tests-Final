package com.adriano.testsfinalproject.services;

import com.adriano.testsfinalproject.models.Book;
import com.adriano.testsfinalproject.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    void mustRemoveABookThatExists() {
        Book bookTest = new Book(
                UUID.randomUUID().toString(),
                "titulo de teste",
                "um resumo",
                "um sumario",
                BigDecimal.valueOf(30.5),
                234,
                "123-123-123-isbn",
                LocalDate.now().plusMonths(6)
        );
        var existingId = bookTest.getId();
        when(bookRepository.findById(existingId)).thenReturn(Optional.of(bookTest));
        doNothing().when(bookRepository).delete(any(Book.class));

        var answer = bookService.deleteBook(existingId);

        assertEquals(bookTest, answer);
    }

    @Test
    void mustThrowExceptionRemovingABookThatDoesntExists() {
        Book bookTest = new Book(
                UUID.randomUUID().toString(),
                "titulo de teste",
                "um resumo",
                "um sumario",
                BigDecimal.valueOf(30.5),
                234,
                "123-123-123-isbn",
                LocalDate.now().plusMonths(6)
        );
        var wrongId = "not-found-id";
        when(bookRepository.findById(wrongId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook(wrongId));
    }

    @Test
    void mustGetABookThatExists() {
        Book bookTest = new Book(
                UUID.randomUUID().toString(),
                "titulo de teste",
                "um resumo",
                "um sumario",
                BigDecimal.valueOf(30.5),
                234,
                "123-123-123-isbn",
                LocalDate.now().plusMonths(6)
        );
        var existingId = bookTest.getId();
        when(bookRepository.findById(existingId)).thenReturn(Optional.of(bookTest));

        var answer = bookService.findById(existingId);

        assertEquals(answer, bookTest);
    }

    @Test
    void mustThrowExceptionGetingABookThatDoesntExists() {
        Book bookTest = new Book(
                UUID.randomUUID().toString(),
                "titulo de teste",
                "um resumo",
                "um sumario",
                BigDecimal.valueOf(30.5),
                234,
                "123-123-123-isbn",
                LocalDate.now().plusMonths(6)
        );
        var wrongId = "not-found-id";
        when(bookRepository.findById(wrongId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bookService.findById(wrongId));
    }


}
