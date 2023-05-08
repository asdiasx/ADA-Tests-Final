package com.adriano.testsfinalproject.services;

import com.adriano.testsfinalproject.models.Book;
import com.adriano.testsfinalproject.models.BookDto;
import com.adriano.testsfinalproject.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public List<BookDto> listBooks() {
        return bookRepository.findAll().stream()
                .map(BookDto::fromBook)
                .toList();
    }

    public BookDto saveBook(BookDto newBook) {
        return BookDto.fromBook(bookRepository.save(Book.fromDTO(newBook)));
    }
}
