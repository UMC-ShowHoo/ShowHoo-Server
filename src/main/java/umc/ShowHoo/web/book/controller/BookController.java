package umc.ShowHoo.web.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.book.converter.BookConverter;
import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.dto.BookResponseDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.service.BookCommandService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookCommandService bookCommandService;

    //공연 예매 API
    @PostMapping("/post")
    @Operation(summary = "공연 예매 API", description = "관람자가 공연 게시글 상세 조회 페이지에서 공연을 예매하기 위한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found"),
    })
    @Parameters({
            @Parameter(name = "audienceId", description = "예매자의 id, NOT NULL"),
            @Parameter(name = "name", description = "예매자의 이름, NOT NULL"),
            @Parameter(name = "phoneNum", description = "예매자의 전화번호, NOT NULL")
    })
    public ApiResponse<BookResponseDTO.postBookDTO> bookTicket(@RequestBody @Valid BookRequestDTO.postDTO request){
        Book book = bookCommandService.postBook(request);
        return ApiResponse.onSuccess(BookConverter.toPostBookDTO(book));
    }
}