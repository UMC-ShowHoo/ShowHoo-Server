package umc.ShowHoo.web.performer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.performer.service.PerformerService;


@RestController
@RequiredArgsConstructor
public class PerformerController {

    private final PerformerService performerService;
    private final PerformerRepository performerRepository;

//    @PostMapping("/performer/")
//    @Operation(summary = "공연자 공연 준비- 큐시트 확인 api",description = "공연 준비중 큐시트 확인 시에 필요한 API")
//    @ApiResponses({
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
//    })
//    public ApiResponse<>
}
