package com.example.demo.report;

import com.example.demo.backend.BookService;
import com.example.demo.backend.model.dto.BookDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfReportService implements ReportService{

    private final BookService bookService;

    @Override
    public String export() {
        List<BookDTO> books = bookService.outOfStock();
        int size = books.size();
        try{
            PDDocument pdf = new PDDocument();
            PDPage page = new PDPage();
            pdf.addPage(page);
            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDPageContentStream contentStream = new PDPageContentStream(pdf,page);
            contentStream.beginText();
            contentStream.setFont(font,16);
            contentStream.showText("Books out of stock");
            contentStream.setFont(font,12);
            contentStream.newLine();
            for (BookDTO book:books) {
                String bookInfo = book.toString();
                contentStream.newLine();
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.close();
            pdf.save("PDFReport.pdf");
        }catch (IOException e){
            return "Failed to create pdf report";
        }
        return "Pdf report successfully generated.";
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
