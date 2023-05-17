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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {

    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    @Test
    void mustRemoveABook() {

        Book bookTest =new Book(
                UUID.randomUUID().toString(),
                "titulo de teste",
                "um resumo",
                "um sumario",
                BigDecimal.valueOf(30.5),
                234,
                "123-123-123-isbn",
                LocalDate.now().plusMonths(6)
        );

//        when(bookRepository.deleteById(anyString())).;
//        doNothing().when(bookRepository).deleteById(anyString());
//        Produto produto = produtoService.removerProduto(1L);
//
//        assertEquals(1L, produto.getId());

    }

}
