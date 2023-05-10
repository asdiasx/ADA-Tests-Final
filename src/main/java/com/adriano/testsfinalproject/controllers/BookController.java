package com.adriano.testsfinalproject.controllers;

import com.adriano.testsfinalproject.models.BookDto;
import com.adriano.testsfinalproject.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/books")
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> listBooks() {
        return bookService.listBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") String id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void removeBook(@PathVariable("id") String id) {
        bookService.delete(id);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable("id") String id, @Valid @RequestBody BookDto updatedBook) {
        return bookService.updateBook(id, updatedBook);
    }

    @PostMapping
    public BookDto registerBook(@Valid @RequestBody BookDto newBook) {
        return bookService.saveBook(newBook);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}