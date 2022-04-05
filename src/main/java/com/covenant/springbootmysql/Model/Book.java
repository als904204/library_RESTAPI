package com.covenant.springbootmysql.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 도서 Model
 */
@Getter
@Setter
@Entity
@Table(name = "book")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String isbn;    //

    // BOOK - Author DB 관계
    /**
     * Book : Author
     * N   :   1
     **/
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonManagedReference
    private Author author;

    // BOOK - Lend 대출 관계
    /**
     * 하나의 책을 여러명이 대출 가능
     * 1 : N
     */
    @JsonBackReference
    @OneToMany(mappedBy = "book",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lend> lends;
}
