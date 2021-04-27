package com.example.demo.backend;

import com.example.demo.backend.model.Book;
import com.example.demo.backend.model.dto.BookDTO;
import com.example.demo.backend.model.dto.BookFilterRequestDTO;
import com.example.demo.backend.model.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDTO> findAll(){
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public void addDummyData() {
        Random random = new Random();
        int nrBooks = 5;
        for(int i = 0 ; i < nrBooks ; i++){
            bookRepository.save(Book.builder()
                    .title(String.valueOf(random.nextFloat()))
                    .author(String.valueOf(random.nextFloat()))
                    .quantity(random.nextInt()).build()
            );
        }
    }

    public BookDTO create(BookDTO book) {
        Book bookToSave = bookMapper.fromDto(book);
        return bookMapper.toDto(bookRepository.save(bookToSave));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book currentBook = findById(id);
        currentBook.setAuthor(book.getAuthor());
        currentBook.setGenre(book.getGenre());
        currentBook.setPrice(book.getPrice());
        currentBook.setQuantity(book.getQuantity());
        currentBook.setTitle(book.getTitle());
        Book bookEdited = bookRepository.save(currentBook);
        return bookMapper.toDto(bookEdited);
    }

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public BookDTO changePrice(Long id, float newPrice) {
        Book actBook = findById(id);
        actBook.setPrice(newPrice);
        return bookMapper.toDto(bookRepository.save(actBook));
    }

    public BookDTO get(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public BookDTO sellBook(Long id) {
        Book actBook = findById(id);
        int currentQuantity = actBook.getQuantity();
        actBook.setQuantity(currentQuantity - 1);
        return bookMapper.toDto(bookRepository.save(actBook));
    }

    public List<BookDTO> outOfStock(){
        List<BookDTO> allBooks = findAll();
        List<BookDTO> outOfStockBooks = allBooks.stream()
                .filter(book -> book.getQuantity() == 0)
                .collect(Collectors.toList());

        return outOfStockBooks;
    }

    public List<BookDTO> filteredBooks(String filter){
        List<BookDTO> books = bookRepository.findAllByTitleLikeOrAuthorLikeOrGenreLike(filter,filter,filter).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
        return books;
    }
}
