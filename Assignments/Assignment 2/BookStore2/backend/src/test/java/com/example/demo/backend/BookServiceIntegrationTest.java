package com.example.demo.backend;

import com.example.demo.backend.model.Book;
import com.example.demo.backend.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        int nrBooks = 10;
        for(int i = 0 ; i <nrBooks; i++){
            bookRepository.save(Book.builder()
                    .title("Title"+Integer.toString(i))
                    .author("Author"+Integer.toString(i))
                    .genre("genre"+Integer.toString(i))
                    .price((float) (i*10.0))
                    .quantity(i)
                    .build()
            );
        }

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(nrBooks, all.size());
    }

    @Test
    void addDummyData() {
    }
}