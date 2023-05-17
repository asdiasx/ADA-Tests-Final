package com.adriano.testsfinalproject.services;

import com.adriano.testsfinalproject.helpers.BookFactory;
import com.adriano.testsfinalproject.models.Book;
import com.adriano.testsfinalproject.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    void mustRemoveABookThatExists() {
Book bookTest = BookFactory.fakeBook("Meu Livro");
        var existingId = bookTest.getId();
        when(bookRepository.findById(existingId)).thenReturn(Optional.of(bookTest));
        doNothing().when(bookRepository).delete(any(Book.class));

        var answer = bookService.deleteBook(existingId);

        assertEquals(bookTest, answer);
    }

    @Test
    void mustThrowExceptionRemovingABookThatDoesntExists() {
        var wrongId = "not-found-id";
        when(bookRepository.findById(wrongId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook(wrongId));
    }

    @Test
    void mustGetABookThatExists() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        var existingId = bookTest.getId();
        when(bookRepository.findById(existingId)).thenReturn(Optional.of(bookTest));

        var answer = bookService.findById(existingId);

        assertEquals(answer, bookTest);
    }

    @Test
    void mustThrowExceptionGettingABookThatDoesntExists() {
        var wrongId = "not-found-id";
        when(bookRepository.findById(wrongId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bookService.findById(wrongId));
    }

}
