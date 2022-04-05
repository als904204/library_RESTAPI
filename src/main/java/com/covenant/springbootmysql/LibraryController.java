package com.covenant.springbootmysql;


import com.covenant.springbootmysql.Model.Author;
import com.covenant.springbootmysql.Model.Book;
import com.covenant.springbootmysql.Model.Member;
import com.covenant.springbootmysql.Model.dto.AuthorCreationRequest;
import com.covenant.springbootmysql.Model.dto.BookCreationRequest;
import com.covenant.springbootmysql.Model.dto.BookLendRequest;
import com.covenant.springbootmysql.Model.dto.MemberCreationRequest;
import com.covenant.springbootmysql.Service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/library") // localhost:8080/api/library
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    /** 도서 로직 start **/
    /**
     * 도서 조회
     *
     * @param isbn
     * @return
     * @RequestParam(required = false) 값이 null 이라면 예외 처리를 하지 않음
     */
    @GetMapping("/book")
    public ResponseEntity readBooks(@RequestParam(required = false) String isbn) {
        if (isbn == null) {
            return ResponseEntity.ok(libraryService.readBooks());
        }
        return ResponseEntity.ok(libraryService.readBook(isbn));
    }

    // bookId get 맵핑
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> readBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.readBook(bookId));
    }

    // 도서 생성
    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody BookCreationRequest request) {
        return ResponseEntity.ok(libraryService.createBook(request));
    }

    // 도서 삭제
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId, HttpServletResponse response) throws IOException {
        PrintWriter w = response.getWriter();
        w.write("bookID : " + bookId);
        libraryService.deleteBook(bookId);

        return ResponseEntity.ok().build();
    }
    /** 도서 로직 end **/


    /** 회원 로직 start **/
    // 회원 추가
    @PostMapping("/member")
    public ResponseEntity<Member> createMember(@RequestBody MemberCreationRequest request) {
        return ResponseEntity.ok(libraryService.createMember(request));
    }

    // 회원 수정
    /**
     * PUT : 리소스의 모든 것을 업데이트 한다.
     * PATCH : 리소스의 일부를 업데이트 한다.
     */
    @PatchMapping("/member/{memberId}")
    public ResponseEntity<Member> updateMember(
            @RequestBody MemberCreationRequest request,
            @PathVariable Long memberId) {
        return ResponseEntity.ok(libraryService.updateMember(memberId, request));
    }

    // 회원 조회
    @GetMapping("/member")
    public ResponseEntity memberList(@RequestParam(required = false) Long memberId) {
        if (memberId == null) {
            return ResponseEntity.ok(libraryService.memberList());
        }
        return ResponseEntity.ok(libraryService.findByMemberId(memberId     ));
    }
    /** 회원 로직 end **/


    // 대출 api
    @PostMapping("/book/lend")
    public ResponseEntity<List<String>> lendBook(@RequestBody BookLendRequest bookLendRequest) {
        return ResponseEntity.ok(libraryService.lendABook(bookLendRequest));
    }

    // 저자 생성
    @PostMapping("/author")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorCreationRequest request) {
        return ResponseEntity.ok(libraryService.createAuthor(request));
    }
}
