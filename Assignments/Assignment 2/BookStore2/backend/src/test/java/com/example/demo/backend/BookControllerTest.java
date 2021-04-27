package com.example.demo.backend;

import com.example.demo.BaseControllerTest;
import com.example.demo.backend.model.dto.BookDTO;
import com.example.demo.report.CsvReportService;
import com.example.demo.report.PdfReportService;
import com.example.demo.report.ReportFactoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.TestCreationFactory.*;
import static com.example.demo.UrlMapping.*;
import static com.example.demo.report.ReportType.CSV;
import static com.example.demo.report.ReportType.PDF;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportFactoryService reportFactoryService;

    @Mock
    private CsvReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    void setup() {
        super.setUp();
        controller = new BookController(bookService, reportFactoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {
        List<BookDTO> books = new ArrayList<>();
        int nrBooks = 10;
        for (int i = 0; i < nrBooks; i++) {
            BookDTO book = newBookDTO();
            books.add(book);
        }

        when(bookService.findAll()).thenReturn(books);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(BOOKS));

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJsonContent = objectMapper.writeValueAsString(books);
        result.andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent, true));
    }

    @Test
    void exportReport() throws Exception {
        when(reportFactoryService.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportFactoryService.getReportService(CSV)).thenReturn(csvReportService);

        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);

        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(MockMvcRequestBuilders.get(BOOKS + EXPORT_REPORT, PDF));
        ResultActions csvExport = mockMvc.perform(MockMvcRequestBuilders.get(BOOKS + EXPORT_REPORT, CSV));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .quantity(randomBoundedInt(200))
                .price(randomFloat()).build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOKS, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .quantity(randomBoundedInt(200))
                .price(randomFloat()).build();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS + ENTITY, reqBook, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void delete() throws Exception {
        long id = randomLong();

        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk());

        verify(bookService,times(1)).delete(id);
    }

    @Test
    void changePrice() throws Exception {
        long id = randomLong();
        float newPrice = randomFloat();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .quantity(randomBoundedInt(200))
                .price(newPrice).build();

        when(bookService.changePrice(id, newPrice)).thenReturn(reqBook);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOKS + ENTITY, newPrice, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void getBook() throws Exception {
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .quantity(randomBoundedInt(200))
                .price(randomFloat()).build();

        when(bookService.get(id)).thenReturn(reqBook);

        ResultActions result = performGetWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void sellBook() throws Exception {
        float newPrice = randomFloat();
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .quantity(randomBoundedInt(200))
                .price(newPrice).build();

        int quantity = reqBook.getQuantity() - 1;
        reqBook.setQuantity(quantity);

        when(bookService.sellBook(id)).thenReturn(reqBook);

        ResultActions result = performPatchWithPathVariable(BOOKS + SELL_BOOK, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

}
