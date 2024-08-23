package umc.ShowHoo.web.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.book.dto.BookResponseDTO;
import umc.ShowHoo.web.book.entity.BookDetail;
import umc.ShowHoo.web.book.service.BookAdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performer")
public class BookAdminController {

    private final BookAdminService bookAdminService;

    @GetMapping("/{showId}/prepare/book-admin")
    @Operation(summary = "공연자 - 공연 준비 시 전체 예매자 조회 API",description = "공연자가 공연 준비 시에 전체 예매자를 조회하기 위한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameter(name = "showId",description = "공연 id")
    public ApiResponse<List<BookResponseDTO.bookInfoDTO>> getAllBookList(@PathVariable(name = "showId") Long showsId){
        List<BookResponseDTO.bookInfoDTO> bookInfoList= bookAdminService.getAllBookList(showsId);
        return ApiResponse.onSuccess(bookInfoList);
    }

    @GetMapping("/{showId}/prepare/book-admin/detail")
    @Operation(summary = "공연자 - 공연 준비 시 예매자 관리 API",description = "공연자가 공연 준비 시에 예매자를 조회하기 위한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameter(name = "showId",description = "공연 id")
    public ApiResponse<List<BookResponseDTO.bookInfoDTO>> getBookList(@PathVariable(name = "showId") Long showsId, @RequestParam(name = "detail") BookDetail detail){
        List<BookResponseDTO.bookInfoDTO> bookInfoList= bookAdminService.getBookInfoList(showsId, detail);
        return ApiResponse.onSuccess(bookInfoList);
    }

    @GetMapping("/{showId}/prepare/book-admin/cancel")
    @Operation(summary = "공연자 - 공연준비 : 환불 요청한 예매자 명단",description = "공연자가 공연 준비 시에 환불 요청한 예매자를 조회하기 위한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameter(name = "showId",description = "공연 id")
    public ApiResponse<List<BookResponseDTO.refundBookDTO>> getRefundBookList(@PathVariable(name = "showId") Long showId){
        List<BookResponseDTO.refundBookDTO> refundBookList=bookAdminService.getRefundBookList(showId);
        return ApiResponse.onSuccess(refundBookList);
    }

    @GetMapping("/{showId}/prepare/book-admin/entrance")
    @Operation(summary = "공연자 - 공연준비 : 입장 관리 조회 APi",description = "공연자가 공연 준비 시에 예매자의 입장 관리 여부를 조회하기 위한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @Parameter(name = "showId",description = "공연 id")
    public ApiResponse<BookResponseDTO.getEntranceListDTO> getEntranceBookList(@PathVariable(name = "showId") Long showId){
        return ApiResponse.onSuccess(bookAdminService.getEntranceList(showId));
    }

    @PutMapping("/book-admin/{bookId}")
    @Operation(summary = "공연자 - 공연준비 : 입장 확인 & 확인 취소 API",description = "공연자가 공연 준비 시에 예매자의 입장 확인 & 확인 취소를 위한 API, 입장 확인 시 : request=true, 입장 확인 취소 시 : request=false")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<Void> confirmEntrance(@PathVariable(name = "bookId") Long bookId, @RequestParam(name = "request") Boolean request){
        bookAdminService.changeEntranceStatus(bookId, request);
        return ApiResponse.onSuccess(null);
    }

}
