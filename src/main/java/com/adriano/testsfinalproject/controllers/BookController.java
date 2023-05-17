package com.adriano.testsfinalproject.controllers;

import com.adriano.testsfinalproject.models.BookDto;
import com.adriano.testsfinalproject.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<BookDto>> listBooks() {
        return ResponseEntity.ok(bookService.listBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") String id) {
        var answer = bookService.findById(id);
        return ResponseEntity.ok(BookDto.fromBook(answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> removeBook(@PathVariable("id") String id) {
        return ResponseEntity.ok(BookDto.fromBook(bookService.delete(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") String id, @Valid @RequestBody BookDto updatedBook) {
        return ResponseEntity.ok(bookService.updateBook(id, updatedBook));
    }

    @PostMapping
    public ResponseEntity<BookDto> registerBook(@Valid @RequestBody BookDto newBook) {
        return ResponseEntity.ok(bookService.saveBook(newBook));
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFoundException(IllegalArgumentException ex) {
        return ("{ \"erro\": \"%s\" }").formatted(ex.getMessage());
    }
}
