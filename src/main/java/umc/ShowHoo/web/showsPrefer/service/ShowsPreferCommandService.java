package umc.ShowHoo.web.showsPrefer.service;

import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferRequestDTO;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferResponseDTO;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;

public interface ShowsPreferCommandService {

    ShowsPreferResponseDTO.createDTO createShowsPrefer(ShowsPreferRequestDTO.createDTO request);

    String deleteShowsPrefer(Long id);
}
