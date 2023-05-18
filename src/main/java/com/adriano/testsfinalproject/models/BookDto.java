package com.adriano.testsfinalproject.models;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;

@Log4j2
public record BookDto(
        String id,
        @NotBlank(message = "O título é obrigatório") String title,
        @NotBlank(message = "O resumo é obrigatório")
        @Size(max = 500, message = "O resumo deve ter no máximo 500 caracteres")
        String synopsis,
        String summary,
        @Min(value = 20, message = "O preço deve ser no mínimo 20") BigDecimal price,
        @Min(value = 100, message = "O número de páginas deve ser no mínimo 100") int numPages,
        @NotBlank(message = "O isbn é obrigatório") String isbn,
        @Future(message = "A data deve ser futura") LocalDate publishDate
) {

    public static BookDto fromBook(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getSynopsis(),
                book.getSummary(),
                book.getPrice(),
                book.getNumPages(),
                book.getIsbn(),
                book.getPublishDate());
    }
}
