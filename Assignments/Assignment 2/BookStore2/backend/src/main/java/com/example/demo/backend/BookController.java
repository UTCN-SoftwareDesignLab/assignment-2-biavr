package com.example.demo.backend;

import com.example.demo.backend.model.dto.BookDTO;
import com.example.demo.report.ReportFactoryService;
import com.example.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportFactoryService reportFactoryService;

    @GetMapping(value = FIRST_TEST)
    public String test(){
        bookService.addDummyData();
        return "This is a test. It works";
    }

    @GetMapping
    public List<BookDTO> findAll(){
        return bookService.findAll();
    }

    @GetMapping(FILTERED)
    public List<BookDTO> filter(@PathVariable String key){
        return bookService.filteredBooks(key);
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book){
        return bookService.create(book);
    }

    @PutMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book){
        return bookService.edit(id, book);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    @PatchMapping(ENTITY)
    public BookDTO changePrice(@PathVariable Long id, @RequestBody float newPrice){
        return bookService.changePrice(id, newPrice);
    }

    @GetMapping(ENTITY)
    public  BookDTO getBook(@PathVariable Long id){
        return bookService.get(id);
    }

    @PatchMapping(SELL_BOOK)
    public BookDTO sellBook(@PathVariable Long id){
        return bookService.sellBook(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type){
        return reportFactoryService.getReportService(type).export();
    }

}
