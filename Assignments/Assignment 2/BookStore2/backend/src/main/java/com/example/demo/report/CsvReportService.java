package com.example.demo.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.example.demo.backend.BookService;
import com.example.demo.backend.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CsvReportService implements ReportService{

    private final BookService bookService;
    private final String columns = "Id,Title,Author,Genre,Price\n";

    @Override
    public String export() {
        List<BookDTO> books = bookService.outOfStock();
        StringBuilder sb = new StringBuilder();
        sb.append(columns);
        try (PrintWriter writer = new PrintWriter(new File("BooksOutOfStock.csv"))) {

            for (BookDTO bookDTO : books) {
                sb.append(bookDTO.getId().toString() + ",");
                sb.append(bookDTO.getTitle() + ",");
                sb.append(bookDTO.getAuthor() + ",");
                sb.append(bookDTO.getGenre() + ",");
                sb.append(Float.toString(bookDTO.getPrice()) + "\n");
            }

            writer.write(sb.toString());
            writer.close();

        } catch (FileNotFoundException e) {
            return "Failed to create csv report";
        }
        return "Csv report successfully generated";
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV;
    }
}
