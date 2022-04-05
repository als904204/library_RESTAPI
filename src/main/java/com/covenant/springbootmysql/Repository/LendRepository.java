package com.covenant.springbootmysql.Repository;

import com.covenant.springbootmysql.Model.Book;
import com.covenant.springbootmysql.Model.Lend;
import com.covenant.springbootmysql.Model.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LendRepository extends JpaRepository<Lend, Long> {
    Optional<Lend> findByBookAndStatus(Book book, LendStatus status);
}
