package com.example.demo.backend.model.mapper;

import com.example.demo.backend.model.Book;
import com.example.demo.backend.model.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toDto(Book book);

    Book fromDto(BookDTO book);
}
