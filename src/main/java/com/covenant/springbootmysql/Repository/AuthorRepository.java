package com.covenant.springbootmysql.Repository;

import com.covenant.springbootmysql.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
