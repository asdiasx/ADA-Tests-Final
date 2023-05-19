package com.adriano.testsfinalproject.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BookTests {

    @Test
    void mustOutputAStringFromBookObject() {
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
