package com.covenant.springbootmysql.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

/**
 * 대출 Model
 */
@Getter
@Setter
@Entity
@Table(name = "lend")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    // 대출시작
    private Instant startOn;

    // 대출종료
    private Instant dueOn;

    @Enumerated(EnumType.ORDINAL)
    private LendStatus status;


    // BOOK - Lend 대출 관계
    /**
     * 하나의 책을 여러명이 대출 가능
     * BOOK - Lend
     * 1 : N
     */
    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private Book book;

    // 회원 - 대출관계
    /**
     * 한 명의 회원이 도서 여러권 대출 가능
     * 회원 : 대출
     * 1: N
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member member;
}
