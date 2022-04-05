package com.covenant.springbootmysql.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 회원 Model
 */
@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    // 활성,비활성 상태
    @Enumerated(EnumType.STRING)
    private MemberStatus status;


    // 회원 - 대출관계
    /**
     * 한 명의 회원이 도서 여러권 대출 가능
     * 회원 대출
     * 1: N
     */
    @JsonBackReference
    @OneToMany(mappedBy = "member",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lend> lends;
}
