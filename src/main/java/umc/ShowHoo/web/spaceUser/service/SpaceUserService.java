package umc.ShowHoo.web.spaceUser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.spaceUser.dto.SpaceUserResponseDTO;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;
import umc.ShowHoo.web.spaceUser.handler.SpaceUserHandler;
import umc.ShowHoo.web.spaceUser.repository.SpaceUserRepository;

@Service
@RequiredArgsConstructor
public class SpaceUserService {
    private final SpaceUserRepository spaceUserRepository;
    public SpaceUserResponseDTO.MyPageDTO getMyPageProfiles(Long spaceUserId) {
        SpaceUser spaceUser = spaceUserRepository.findById(spaceUserId)
                .orElseThrow(() -> new SpaceUserHandler(ErrorStatus.SPACEUSER_NOT_FOUND));;
        return new SpaceUserResponseDTO.MyPageDTO(
                spaceUser.getMember().getName(),
                spaceUser.getMember().getProfileimage()
        );
    }
}
