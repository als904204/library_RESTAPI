package com.covenant.springbootmysql.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 저자 Model
 */

@Getter
@Setter
@Entity
@Table(name = "author")

public class Author {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;


    /**
     * *  Author is can write a lot of books
     *  * Author : book
     *  * 1 : N
     *  **/
    @JsonBackReference
    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books;
}
