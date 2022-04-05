package com.covenant.springbootmysql.Service;

import com.covenant.springbootmysql.Model.Book;
import com.covenant.springbootmysql.Repository.AuthorRepository;
import com.covenant.springbootmysql.Repository.BookRepository;
import com.covenant.springbootmysql.Repository.LendRepository;
import com.covenant.springbootmysql.Repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LibraryServiceTest {

    private final AuthorRepository authorRepository;

    private final MemberRepository memberRepository;

    private final LendRepository lendRepository;

    private final BookRepository bookRepository;

    LibraryServiceTest(AuthorRepository authorRepository, MemberRepository memberRepository, LendRepository lendRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.memberRepository = memberRepository;
        this.lendRepository = lendRepository;
        this.bookRepository = bookRepository;
    }



    @AfterEach
    void cleanUp() {
           authorRepository.deleteAll();
           memberRepository.deleteAll();
           lendRepository.deleteAll();
           bookRepository.deleteAll();
    }

    @Test
    void readBook() {

    }

}