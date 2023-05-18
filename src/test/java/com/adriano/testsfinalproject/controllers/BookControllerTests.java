package com.adriano.testsfinalproject.controllers;

import com.adriano.testsfinalproject.helpers.BookFactory;
import com.adriano.testsfinalproject.models.Book;
import com.adriano.testsfinalproject.models.BookDto;
import com.adriano.testsfinalproject.services.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTests {

    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    @Test
    @DisplayName("Must create the book successfully.")
    void mustCreakeANewBook(){
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        BookDto bookDtoTest = BookDto.fromBook(bookTest);
        when(bookService.saveBook(bookDtoTest)).thenReturn(bookDtoTest);

        var answer = bookController.registerBook(bookDtoTest);

        assertTrue(answer.getStatusCode().is2xxSuccessful());
        assertEquals(BookDto.fromBook(bookTest),answer.getBody());

    }
    @Test
    void mustRemoveABookThatExists() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        var existingId = bookTest.getId();
        doReturn(bookTest).when(bookService).deleteBook(existingId);

        var answer = bookController.removeBook(existingId);

        assertTrue(answer.getStatusCode().is2xxSuccessful());
        assertEquals(BookDto.fromBook(bookTest), answer.getBody());
    }

    @Test
    void mustThrowExceptionRemovingABookThatDoesntExists() {
        var wrongId = "not-found-id";
        when(bookService.deleteBook(wrongId)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> bookController.removeBook(wrongId));
    }

    @Test
    void mustGetABookThatExists() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        var existingId = bookTest.getId();
        doReturn(bookTest).when(bookService).findById(existingId);

        var answer = bookController.getBook(existingId);

        assertTrue(answer.getStatusCode().is2xxSuccessful());
        assertEquals(BookDto.fromBook(bookTest), answer.getBody());
    }

    @Test
    void mustThrowExceptionGettingABookThatDoesntExists() {
        var wrongId = "not-found-id";
        when(bookService.findById(wrongId)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> bookController.getBook(wrongId));
    }

    @Test
    void mustListAllBooks() {
        List<BookDto> bookDtos = List.of(
                BookDto.fromBook(BookFactory.fakeBook("Livro 1")),
                BookDto.fromBook(BookFactory.fakeBook("Livro 2")),
                BookDto.fromBook(BookFactory.fakeBook("Livro 3"))
        );

        when(bookService.listBooks()).thenReturn(bookDtos);

        var answer = bookController.listBooks().getBody();

        assert answer != null;
        assertEquals(3, answer.size());
        assertEquals(bookDtos.get(1).title(), answer.get(1).title());
    }

    @Test
    void mustListAllBooksReturnsEmptyWhenNoBook() {
        List<BookDto> bookDtos = List.of();
        when(bookService.listBooks()).thenReturn(bookDtos);

        var answer = bookController.listBooks().getBody();

        assert answer != null;
        assertTrue(answer.isEmpty());
    }
}
