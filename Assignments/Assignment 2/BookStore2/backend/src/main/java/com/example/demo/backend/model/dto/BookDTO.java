package com.example.demo.backend.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private int quantity;
    private float price;

    @Override
    public String toString(){
        return "Title: " + title + "\n" + "Author: " + author + "\n"
                + "Genre: " + genre + "\n" + "Price: " + Float.toString(price);
    }
}
