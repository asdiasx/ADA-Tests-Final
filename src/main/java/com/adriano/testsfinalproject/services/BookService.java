package com.adriano.testsfinalproject.services;

import com.adriano.testsfinalproject.models.Book;
import com.adriano.testsfinalproject.models.BookDto;
import com.adriano.testsfinalproject.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
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

    public Book findById(String id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Id não encontrado")
        );
    }

    public Book delete(String id) {
        var book = findById(id);
        bookRepository.delete(book);
        return book;
    }

    public BookDto updateBook(String id, BookDto updatedBook) {
        var book = Book.fromDTO(updatedBook);
        book.setId(id);
        delete(id);
        return BookDto.fromBook(bookRepository.save(book));
    }
}
