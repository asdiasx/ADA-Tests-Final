package com.adriano.testsfinalproject.controllers;

import com.adriano.testsfinalproject.helpers.BookFactory;
import com.adriano.testsfinalproject.models.Book;
import com.adriano.testsfinalproject.models.BookDto;
import com.adriano.testsfinalproject.services.BookService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerTests {

    private static Validator validator;
    @InjectMocks
    BookController bookController;
    @Mock
    BookService bookService;

    @BeforeAll
    static void setup() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void mustCreateANewBook() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        BookDto bookDtoTest = BookDto.fromBook(bookTest);
        when(bookService.saveBook(bookDtoTest)).thenReturn(bookDtoTest);

        var answer = bookController.registerBook(bookDtoTest);

        assertTrue(answer.getStatusCode().is2xxSuccessful());
        assertEquals(BookDto.fromBook(bookTest), answer.getBody());

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

    @Test
    void mustUpdateABookWithSuccess() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        Book bookUpdate = BookFactory.fakeBook("Meu Livro - Updated");
        BookDto bookDtoUpdate = BookDto.fromBook(bookUpdate);
        BookDto bookTestDto = BookDto.fromBook(bookTest);
        var existingId = bookTest.getId();
        when(bookService.updateBook(anyString(), Mockito.any(BookDto.class))).thenReturn(bookTestDto);


        var answer = bookController.updateBook(existingId, bookDtoUpdate);


        assertTrue(answer.getStatusCode().is2xxSuccessful());
        assertEquals(bookTestDto, answer.getBody());

    }

    @Test
    void mustNotValidatedWhenABookDontHaveATitle() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        bookTest.setTitle("");
        BookDto bookDtoTest = BookDto.fromBook(bookTest);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDtoTest);
        assertEquals(1, violations.size());

        ConstraintViolation<BookDto> violation = violations.iterator().next();
        assertEquals("O título é obrigatório", violation.getMessage());
        assertEquals("title", violation.getPropertyPath().toString());

    }

    @Test
    void mustNotValidatedWhenIsbnIsBlank() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        bookTest.setIsbn("");
        BookDto bookDtoTest = BookDto.fromBook(bookTest);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDtoTest);
        assertEquals(1, violations.size());

        ConstraintViolation<BookDto> violation = violations.iterator().next();
        assertEquals("O isbn é obrigatório", violation.getMessage());
        assertEquals("isbn", violation.getPropertyPath().toString());
    }

    @Test
    void mustNotValidatedPriceIsLessThan20() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        bookTest.setPrice(BigDecimal.TEN);
        BookDto bookDtoTest = BookDto.fromBook(bookTest);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDtoTest);

        assertEquals(1, violations.size());
        ConstraintViolation<BookDto> violation = violations.iterator().next();
        assertEquals("O preço deve ser no mínimo 20", violation.getMessage());
        assertEquals("price", violation.getPropertyPath().toString());
    }

    @Test
    void mustNotValidatedWhenNumPageIsLessThan100() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        bookTest.setNumPages(80);
        BookDto bookDtoTest = BookDto.fromBook(bookTest);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDtoTest);

        assertEquals(1, violations.size());
        ConstraintViolation<BookDto> violation = violations.iterator().next();
        assertEquals("O número de páginas deve ser no mínimo 100", violation.getMessage());
        assertEquals("numPages", violation.getPropertyPath().toString());
    }

    @Test
    void mustNotValidatedWhenDateIsInThePast() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        bookTest.setPublishDate(LocalDate.now().minusDays(1));
        BookDto bookDtoTest = BookDto.fromBook(bookTest);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDtoTest);

        assertEquals(1, violations.size());
        ConstraintViolation<BookDto> violation = violations.iterator().next();
        assertEquals("A data deve ser futura", violation.getMessage());
        assertEquals("publishDate", violation.getPropertyPath().toString());
    }

    @Test
    void mustNotValidatedWhenSynopsisIsBlank() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        bookTest.setSynopsis("");
        BookDto bookDtoTest = BookDto.fromBook(bookTest);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDtoTest);

        assertEquals(1, violations.size());
        ConstraintViolation<BookDto> violation = violations.iterator().next();
        assertEquals("O resumo é obrigatório", violation.getMessage());
        assertEquals("synopsis", violation.getPropertyPath().toString());
    }

    @Test
    void mustNotValidatedWhenSynopsisIsBiggerThan500Character() {
        Book bookTest = BookFactory.fakeBook("Meu Livro");
        bookTest.setSynopsis("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur posuere non neque " +
                "non ultrices. Aenean fermentum felis risus, et eleifend ante auctor ultricies. Morbi pharetra mi at" +
                " tincidunt dictum. Nam tristique tempor massa quis bibendum. Morbi venenatis nec risus ut commodo." +
                " Integer in dolor quis ante ornare tincidunt nec convallis turpis. Morbi mattis pharetra porta." +
                " Duis sed lacus euismod, tincidunt erat scelerisque, porttitor enim. Nullam magna augue, vestibulum" +
                " at massa vitae accumsan.");
        BookDto bookDtoTest = BookDto.fromBook(bookTest);

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDtoTest);

        assertEquals(1, violations.size());
        ConstraintViolation<BookDto> violation = violations.iterator().next();
        assertEquals("O resumo deve ter no máximo 500 caracteres", violation.getMessage());
        assertEquals("synopsis", violation.getPropertyPath().toString());
    }


}
