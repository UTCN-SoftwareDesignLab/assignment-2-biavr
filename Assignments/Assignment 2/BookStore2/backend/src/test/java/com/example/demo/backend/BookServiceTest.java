package com.example.demo.backend;

import com.example.demo.backend.model.Book;
import com.example.demo.backend.model.dto.BookDTO;
import com.example.demo.backend.model.mapper.BookMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.TestCreationFactory.randomFloat;
import static org.mockito.Mockito.*;

class BookServiceTest extends AbsUnitTest{

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    public void beforeEach(){
        super.setup();
        bookService = new BookService(bookRepository, bookMapper);
        //bookRepository.deleteAll();
    }
    @Test
    void findAll() {
        List<Book> books = new ArrayList<>();
        int nrBooks = 10;
        for(int i = 0 ; i <nrBooks; i++){
            books.add(Book.builder()
                    .title("Title"+Integer.toString(i))
                    .author("Author"+Integer.toString(i))
                    .id((long) i)
                    .genre("genre"+Integer.toString(i))
                    .price((float) (i*10.0))
                    .quantity(i)
                    .build()
            );
        }

        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        verify(bookRepository, times(1)).findAll();

        Assertions.assertEquals(books.size(),all.size());
    }

    @Test
    void delete() {
        List<Book> books = new ArrayList<>();
        int nrBooks = 5;
        for(int i = 0 ; i <nrBooks; i++){
            books.add(Book.builder()
                    .title("Title"+Integer.toString(i))
                    .author("Author"+Integer.toString(i))
                    .id((long) i)
                    .genre("genre"+Integer.toString(i))
                    .price((float) (i*10.0))
                    .quantity(i)
                    .build()
            );
        }

        when(bookRepository.findAll()).thenReturn(books.subList(1,books.size()));

        Long idToDelete = books.get(0).getId();
        bookService.delete(idToDelete);

        verify(bookRepository, times(1)).deleteById(any());

        List<BookDTO> all = bookService.findAll();
        Assertions.assertEquals(nrBooks - 1,all.size());
    }

    @Test
    void create(){
        BookDTO book = newBookDTO();
        Book book1 = Book.builder()
                .title(randomString())
                .author(randomString())
                .quantity(randomBoundedInt(200))
                .price(randomFloat()).build();
        Book result = bookRepository.save(book1);
        System.out.println(result);

        List<BookDTO> all = bookService.findAll();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void addDummyData() {
        bookService.addDummyData();
        verify(bookRepository,times(5)).save(any());
    }

}