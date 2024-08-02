package umc.ShowHoo.web.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.book.converter.BookConverter;
import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.dto.BookResponseDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.service.BookCommandService;
import umc.ShowHoo.web.book.service.BookQueryService;
import umc.ShowHoo.web.cancelBook.entity.CancelBook;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookCommandService bookCommandService;

    private final BookQueryService bookQueryService;

    //공연 예매 API
    //공연 정보 엔티티 생성 시 포함시켜서 수정할 것.
    @PostMapping("/post")
    @Operation(summary = "공연 예매 API", description = "관람자가 공연 게시글 상세 조회 페이지에서 공연을 예매하기 위한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "audienceId", description = "예매자의 id, NOT NULL"),
            @Parameter(name = "showsId", description = "예매 할 공연의 id, NOT NULL"),
            @Parameter(name = "ticketNum", description = "예매 할 티켓의 매수, 최소 1개"),
            @Parameter(name = "name", description = "예매자의 이름, NOT NULL"),
            @Parameter(name = "phoneNum", description = "예매자의 전화번호, NOT NULL")
    })
    public ApiResponse<BookResponseDTO.postBookDTO> bookTicket(@RequestBody @Valid BookRequestDTO.postDTO request){
        Book book = bookCommandService.postBook(request);
        if (book == null) {
            return ApiResponse.onSuccess(BookResponseDTO.postBookDTO.builder()
                    .alert("예매할 수 있는 티켓 매수를 초과하였습니다.")
                    .build());
        }
        return ApiResponse.onSuccess(BookConverter.toPostBookDTO(book));
    }

    //예매 내역 조회 API
    @GetMapping("/{audienceId}/ticket")
    @Operation(summary = "예매 내역 조회 API", description = "관람자가 예매하여 승인 대기 중이거나 예매 완료된 공연 예매 내역을 조회하기 위한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "audienceId", description = "예매자의 id, pathVariable"),
            @Parameter(name = "page", description = "페이지 번호")
    })
    public ApiResponse<BookResponseDTO.getBookListDTO> getTicketList(@PathVariable(name = "audienceId") Long audienceId, @RequestParam(name = "page") Integer page){
        Page<Book> bookList = bookQueryService.getTickets(audienceId, page);
        return ApiResponse.onSuccess(BookConverter.toGetBookListDTO(bookList));
    }

    //관람 내역 조회 API
    @GetMapping("/{audienceId}/watched")
    @Operation(summary = "관람 내역 조회 API", description = "관람자의 관람 내역을 조회하기 위한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "audienceId", description = "예매자의 id, pathVariable"),
            @Parameter(name = "page", description = "페이지 번호")
    })
    public ApiResponse<BookResponseDTO.getBookListDTO> getWatchedList(@PathVariable(name = "audienceId") Long audienceId, @RequestParam(name = "page") Integer page){
        Page<Book> bookList = bookQueryService.getWatchedTickets(audienceId, page);
        return ApiResponse.onSuccess(BookConverter.toGetBookListDTO(bookList));
    }

    //마이페이지 다음 예매 내역 조회 API
    @GetMapping("/{audienceId}/next")
    @Operation(summary = "마이페이지/다음 예매 내역 조회 API", description = "관람자의 예매 내역 중 가장 공연 일자가 빠른 예매 내역을 조회 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "audienceId", description = "예매자의 id, pathVariable"),
    })
    public ApiResponse<BookResponseDTO.getBookDTO> getMyPage(@PathVariable(name = "audienceId") Long audienceId){
        Book nextBook = bookQueryService.getNextBook(audienceId);
        return ApiResponse.onSuccess(BookConverter.toGetBookDTO(nextBook));
    }

    //예매 취소 요청 API
    @PutMapping("/{bookId}/cancel")
    @Operation(summary = "예매 취소 요청 API", description = "예매 내역 취소 요청을 보내는 API, 예약 정보의 status 변경과 동시에 공연자에게 취소 정보 전달")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "bookId", description = "예매 내역의 id, pathVariable"),
            @Parameter(name = "name", description = "예매자의 이름"),
            @Parameter(name = "bankName", description = "환불 받고자 하는 은행의 이름"),
            @Parameter(name = "account", description = "환불 받고자 하는 계좌의 번호"),
            @Parameter(name = "reason", description = "취소 사유"),
    })
    public ApiResponse<BookResponseDTO.deleteResponseDTO> requestCancel(@PathVariable(name = "bookId") Long bookId ,@RequestBody BookRequestDTO.deleteBookDTO request){
        CancelBook cancelBook = bookCommandService.requestCancel(bookId, request);

        if(cancelBook == null){
            return ApiResponse.onSuccess(BookResponseDTO.deleteResponseDTO.builder()
                    .alert("이미 취소 요청되었습니다.")
                    .build());
        }
        return ApiResponse.onSuccess(BookConverter.toDeleteBookDTO(cancelBook));
    }

    //예매 내역 상태 변경 API
    //예매 확인(BOOK/CONFIRMED)
    @PutMapping("/{bookId}/confirmed")
    @Operation(summary = "상태 변경 API/예매 확인", description = "예매 내역의 상태를 예매 확인으로 변경하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "bookId", description = "변경하고자 하는 예매 내역의 id, pathVariable"),
    })
    public ApiResponse<BookResponseDTO.changeStatusResponseDTO> requestConfirmed(@PathVariable(name = "bookId") Long bookId){
        Book book = bookCommandService.requestConfirm(bookId);

        if(book == null){
            return ApiResponse.onSuccess(BookResponseDTO.changeStatusResponseDTO.builder()
                    .alert("변경하고자 하는 예매 내역이 현재 '승인 예정' 상태가 아닙니다.")
                    .build());
        }

        return ApiResponse.onSuccess(BookResponseDTO.changeStatusResponseDTO.builder()
                .bookId(book.getId())
                .status(book.getStatus())
                .detail(book.getDetail())
                .alert("예매가 승인되었습니다.")
                .build());
    }

    //예매 취소 확인(CANCEL/CANCELLED)
    @PutMapping("/{bookId}/canceled")
    @Operation(summary = "상태 변경 API/취소 완료", description = "예매 내역의 상태를 취소 완료로 변경하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "bookId", description = "변경하고자 하는 예매 내역의 id, pathVariable"),
    })
    public ApiResponse<BookResponseDTO.changeStatusResponseDTO> requestCanceled(@PathVariable(name = "bookId") Long bookId){
        Book book = bookCommandService.requestCanceled(bookId);

        if(book == null){
            return ApiResponse.onSuccess(BookResponseDTO.changeStatusResponseDTO.builder()
                    .alert("변경하고자 하는 예매 내역이 현재 '취소 대기' 상태가 아닙니다.")
                    .build());
        }

        return ApiResponse.onSuccess(BookResponseDTO.changeStatusResponseDTO.builder()
                .bookId(book.getId())
                .status(book.getStatus())
                .detail(book.getDetail())
                .alert("취소되었습니다.")
                .build());
    }

    //관람 완료(WATCHED/WATCHED)
    @PutMapping("/{bookId}/watched")
    @Operation(summary = "상태 변경 API/관람 완료", description = "예매 내역의 상태를 관람 완료로 변경하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "bookId", description = "변경하고자 하는 예매 내역의 id, pathVariable"),
    })
    public ApiResponse<BookResponseDTO.changeStatusResponseDTO> requestWatched(@PathVariable(name = "bookId") Long bookId){
        Book book = bookCommandService.requestWatched(bookId);

        if(book == null){
            return ApiResponse.onSuccess(BookResponseDTO.changeStatusResponseDTO.builder()
                    .alert("변경하고자 하는 예매 내역이 현재 '예매 완료' 상태가 아닙니다.")
                    .build());
        }

        return ApiResponse.onSuccess(BookResponseDTO.changeStatusResponseDTO.builder()
                .bookId(book.getId())
                .status(book.getStatus())
                .detail(book.getDetail())
                .alert("공연이 종료되었습니다.")
                .build());
    }

}
