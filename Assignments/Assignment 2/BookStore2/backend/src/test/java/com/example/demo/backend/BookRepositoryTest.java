package com.example.demo.backend;

import com.example.demo.backend.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.TestCreationFactory.randomFloat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testValidation(){
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () ->bookRepository.save(Book.builder()
                        .author("adffa")
                        .title("dvadfvssssfdsgvdfgfggggggtyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyytttttttttttttttttttttttttttttttttttttttttttttttttttgggggggggggggggggggggggggggggggggggggggggggeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeegggggggggggga")
                        .build())
        );
    }

    @Test
    void create(){
        Book book = Book.builder()
                .title(randomString())
                .author(randomString())
                .quantity(randomBoundedInt(200))
                .price(randomFloat()).build();
        bookRepository.save(book);

        List<Book> all = bookRepository.findAll();
        Assertions.assertEquals(1, all.size());

    }

    @Test
    void testFilteredBooks() {
        final Book book1 = Book.builder()
                .title("Pride and Prejudice")
                .author("Jane Austine")
                .genre("Romance")
                .price(12.5f)
                .build();
        bookRepository.save(book1);

        List<Book> res1 = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike("%and%","...","...");
        assertFalse(res1.isEmpty());
        assertEquals(1, res1.size());
        assertEquals(book1.getId(),res1.get(0).getId());
    }

}