package com.example.demo.backend;


import com.example.demo.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByTitleLikeOrAuthorLikeOrGenreLike(String title, String author, String genre);
}
