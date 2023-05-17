package com.adriano.testsfinalproject.helpers;

import com.adriano.testsfinalproject.models.Book;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;


public class BookFactory {
    public static Book fakeBook(String title) {
        Lorem lorem = LoremIpsum.getInstance();
        return new Book(
                UUID.randomUUID().toString(),
                title,
                lorem.getParagraphs(1,1),
                lorem.getParagraphs(2,3),
                BigDecimal.valueOf(Math.random()*100+20).setScale(2, RoundingMode.HALF_UP),
                (int) (Math.random()*200+100),
                ("%3d-%3d-isbn").formatted((int)(Math.random()*999), (int)(Math.random()*999)),
                LocalDate.now().plusDays((int)(Math.random()*90+180))
        );
    }
}
