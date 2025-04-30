package ddururi.bookbookclub.domain.book.controller;

import ddururi.bookbookclub.domain.book.dto.BookRequest;
import ddururi.bookbookclub.domain.book.dto.BookResponse;
import ddururi.bookbookclub.domain.book.service.BookService;
import ddururi.bookbookclub.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 책 관련 API 컨트롤러
 * - 등록
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * 책 등록 API
     * @param request 책 등록 요청 데이터
     * @return 등록된 책 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(
            @RequestBody @Valid BookRequest request
    ) {
        BookResponse bookResponse = bookService.createBook(request);
        return ResponseEntity.ok(ApiResponse.success(bookResponse));
    }
}
