package com.covenant.springbootmysql.Repository;

import com.covenant.springbootmysql.Model.Author;
import com.covenant.springbootmysql.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
}
