package umc.ShowHoo.web.rentalFile.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.Shows.repository.ShowsRepository;
import umc.ShowHoo.web.rentalFile.dto.RentalFileResponseDTO;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RentalFileController {
    private final ShowsRepository showsRepository;

    @PostMapping(value = "/performer/{showId}/prepare",consumes = "multipart/form-data")
    @Operation(summary="공연자 - 공연준비 큐시트 작성 API", description = "공연자가 큐시트 작성 시에 제출해야하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponses<RentalFileResponseDTO.PerformerSaveDTO> createPerformerFile(
            @PathVariable Long showId,
            @RequestPart(required = false)MultipartFile setList,
            @RequestPart(required = false)MultipartFile rentalTime,
            @RequestPart(required = false)MultipartFile addOrder){
        Optional<Shows> optionalShows =showsRepository.findById((showId));

    }
}
