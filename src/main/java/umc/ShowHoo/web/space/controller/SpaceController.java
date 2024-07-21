package umc.ShowHoo.web.space.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umc.ShowHoo.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import org.springframework.web.bind.annotation.RestController;
import umc.ShowHoo.jwt.JwtTokenProvider;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.member.repository.MemberRepository;
import umc.ShowHoo.web.space.converter.SpaceConverter;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.service.SpaceService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SpaceController {
    private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);
    private final SpaceService spaceService;
    private final MemberRepository memberRepository;

    @PostMapping("/spaces/{memberId}")
    @Operation(summary = "공연장 등록 API", description = "공연장 등록할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.ResultDTO> createSpace(@PathVariable Long memberId, @RequestBody SpaceRequestDTO spaceRequestDTO) {
        logger.info("Received request to create space: {}", spaceRequestDTO);
        try {
            // memberId로 Member 조회
            Optional<Member> optionalMember = memberRepository.findById(memberId);
            Member member = optionalMember.orElse(null);
            if (member == null) {
                return ApiResponse.onFailure("NOT_FOUND", "Member not found for given memberId", null);
            }

            // Space 엔티티 생성 및 Member 설정
            Space space = SpaceConverter.toEntity(spaceRequestDTO);
            space.setMember(member);

            // Space 저장
            Space savedSpace = spaceService.saveSpace(space);
            SpaceResponseDTO.ResultDTO result = SpaceConverter.toSpaceResponseDTO(savedSpace);

            return ApiResponse.onSuccess(result);

        } catch (SpaceHandler e) {
            logger.error("SpaceHandler error occurred while creating space", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating space", e);
            return ApiResponse.onFailure("ERROR_CODE", "Unexpected error occurred while creating space", null);
        }
    }

}
