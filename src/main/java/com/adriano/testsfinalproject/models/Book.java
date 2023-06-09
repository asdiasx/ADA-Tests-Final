package com.adriano.testsfinalproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKS")
public class Book {
    @Id
    private String id;
    private String title;
    @Lob
    private String synopsis;
    @Lob
    private String summary;
    private BigDecimal price;
    private int numPages;
    private String isbn;
    private LocalDate publishDate;

    public static Book fromDTO(BookDto bookDto) {
        var newBook = new Book();
        newBook.id = UUID.randomUUID().toString();
        newBook.title = bookDto.title();
        newBook.synopsis = bookDto.synopsis();
        newBook.summary = bookDto.summary();
        newBook.price = bookDto.price();
        newBook.numPages = bookDto.numPages();
        newBook.isbn = bookDto.isbn();
        newBook.publishDate = bookDto.publishDate();
        return newBook;
    }
}
