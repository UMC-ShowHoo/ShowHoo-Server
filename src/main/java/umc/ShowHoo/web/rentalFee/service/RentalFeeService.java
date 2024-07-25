package umc.ShowHoo.web.rentalFee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.rentalFee.repository.RentalFeeRepository;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;

import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RentalFeeService {
    private final RentalFeeRepository rentalFeeRepository;

    public SpaceResponseDTO.SpaceDateDTO getSpaceDate(Long spaceUserId, LocalDate date) {
        java.time.DayOfWeek javaDayOfWeek = date.getDayOfWeek();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(javaDayOfWeek.name());
        RentalFee rentalFee = rentalFeeRepository.findBySpaceIdAndDayOfWeek(spaceUserId, dayOfWeek);
        return new SpaceResponseDTO.SpaceDateDTO(rentalFee.getFee());
    }


}
