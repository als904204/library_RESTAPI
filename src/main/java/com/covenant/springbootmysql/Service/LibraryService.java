package com.covenant.springbootmysql.Service;

import com.covenant.springbootmysql.Model.*;
import com.covenant.springbootmysql.Model.dto.AuthorCreationRequest;
import com.covenant.springbootmysql.Model.dto.BookCreationRequest;
import com.covenant.springbootmysql.Model.dto.BookLendRequest;
import com.covenant.springbootmysql.Model.dto.MemberCreationRequest;
import com.covenant.springbootmysql.Repository.AuthorRepository;
import com.covenant.springbootmysql.Repository.BookRepository;
import com.covenant.springbootmysql.Repository.LendRepository;
import com.covenant.springbootmysql.Repository.MemberRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;


    /**
     * 1. id 를 기준으로 DB의 도서 조회
     * @param id
     * @return 해당 도서 id
     */
    public Book readBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Can't find any book under given ID(BookID) : " + id );
    }


    /**
     * 2. DB 에 저장된 모든 도서 조회
     * @return List<Book>
     */
    public List<Book> readBooks() {
        return bookRepository.findAll();
    }

    /**
     * 2 - 1. 요청 받은 isbn 번호로 도서 조회
     * @param isbn
     * @return
     */
    public Book readBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given isbn ID");
    }


    /**
     * 3. isbn(국제표준도서번호) 를 기준으로 DB의 도서 조회
     * @param isbn
     * @return 해당 도서 id
     */
    public Book readBookByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Can't find any book under given ISBN) : " + isbn );
    }


    /**
     * 4. BookCreationRequest(DTO) 로 도서 생성
     * @param book
     * @return bookToCreate
     */
    public Book createBook(BookCreationRequest book) {
        Optional<Author> author = authorRepository.findById(book.getAuthorId());
        // Null 이라면
        if (!author.isPresent()) {
            throw new EntityNotFoundException("Author not found");
        }

        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book, bookToCreate);
        bookToCreate.setAuthor(author.get());
        return bookRepository.save(bookToCreate);
    }

    /**
     * 5. id 를 기준으로 도서 삭제
     * @param id
     */
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    /**
       6. MemberCreationRequest 로 회원 생성
     */
    public Member createMember(MemberCreationRequest request) {
        Member member = new Member();
        member.setStatus(MemberStatus.ACTIVE);
        BeanUtils.copyProperties(request, member);
        return memberRepository.save(member);
    }


    /**
         6 - 1. MemberList 조회
     */
    public List<Member> memberList() {
        return memberRepository.findAll();
    }

    /**
     * 6 -2. 입력된 memberId 로 member 조회
     */
    public Member findByMemberId(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            return member.get();
        }
        throw new EntityNotFoundException("Cant find any member under given memberId");
    }

    /**
     * 7. id 에 해당하는 회원을 MemberCreationRequest 로 변경
     * @param id
     * @param request
     * @return
     */
    public Member updateMember(Long id, MemberCreationRequest request) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent()) {
            throw new EntityNotFoundException(
                    "can't find in th database");
        }

        Member member = optionalMember.get();
        member.setLastName(request.getLastName());
        member.setFirstName(request.getFirstName());
        return memberRepository.save(member);
    }


    /**
     * 8. AuthorCreationRequest 로 저자 생성
     * @param request
     * @return
     */
    public Author createAuthor (AuthorCreationRequest request) {
        Author author = new Author();
        BeanUtils.copyProperties(request, author);
        return authorRepository.save(author);
    }


    /**
     * 9. BookLendRequest 로 도서 대출
     * @param
     * @return
     */
    public List<String> lendABook (BookLendRequest request) {

        Optional<Member> memberForId = memberRepository.findById(request.getMemberId());
        if (!memberForId.isPresent()) {
            throw new EntityNotFoundException("Member not present in the database");
        }

        Member member = memberForId.get();
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new RuntimeException("User is not active to proceed a lending.");
        }

        // 대출 승인
        List<String> booksApprovedToBurrow = new ArrayList<>();

        request.getBookIds().forEach(bookId -> {

            Optional<Book> bookForId = bookRepository.findById(bookId);
            if (!bookForId.isPresent()) {
                throw new EntityNotFoundException("Cant find any book under given ID");
            }

            Optional<Lend> burrowedBook = lendRepository.findByBookAndStatus(bookForId.get(), LendStatus.BURROWED);
            if (!burrowedBook.isPresent()) {
                booksApprovedToBurrow.add(bookForId.get().getName());
                Lend lend = new Lend();
                lend.setMember(memberForId.get());
                lend.setBook(bookForId.get());
                lend.setStatus(LendStatus.BURROWED);
                lend.setStartOn(Instant.now());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                lendRepository.save(lend);
            }

        });
        return booksApprovedToBurrow;
    }

}
