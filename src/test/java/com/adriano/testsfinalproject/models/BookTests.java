package com.adriano.testsfinalproject.models;

import com.adriano.testsfinalproject.repositories.BookRepository;
import com.adriano.testsfinalproject.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BookTests {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    void mustRemoveABookThatExists() {
        Book bookTest = new Book(
                "id",
                "titulo",
                "resumo",
                "sumario",
                BigDecimal.valueOf(20),
                100,
                "isbn",
                LocalDate.of(2099, 12,31)
        );
        assertEquals("Book(id=id, title=titulo, synopsis=resumo," +
                " summary=sumario, price=20, numPages=100, isbn=isbn, " +
                "publishDate=2099-12-31)", bookTest.toString());
    }

}
