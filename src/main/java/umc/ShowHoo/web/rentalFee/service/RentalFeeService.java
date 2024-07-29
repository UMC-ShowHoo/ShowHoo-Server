package umc.ShowHoo.web.rentalFee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.rentalFee.repository.RentalFeeRepository;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;

import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceAdditionalService.repository.SpaceAdditionalServiceRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalFeeService {
    private final RentalFeeRepository rentalFeeRepository;
    private final SpaceAdditionalServiceRepository spaceAdditionalServiceRepository;


    public SpaceResponseDTO.SpacePriceDTO getSpaceDate(Long spaceUserId, LocalDate date, List<String> additionalServices) {
        java.time.DayOfWeek javaDayOfWeek = date.getDayOfWeek();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(javaDayOfWeek.name());

        // 요일별 기본 대관비 조회
        RentalFee rentalFee = rentalFeeRepository.findBySpaceIdAndDayOfWeek(spaceUserId, dayOfWeek);
        int basePrice = rentalFee.getFee();

        // 추가 서비스 가격 계산
        int additionalServicePrice = 0;
        if (additionalServices != null) {
            for (String serviceTitle : additionalServices) {
                SpaceAdditionalService service = spaceAdditionalServiceRepository.findBySpaceIdAndTitle(spaceUserId, serviceTitle);
                if (service != null) {
                    additionalServicePrice += Integer.parseInt(service.getPrice());
                }
            }
        }
        int totalPrice = basePrice + additionalServicePrice;

        return new SpaceResponseDTO.SpacePriceDTO(basePrice, additionalServicePrice, totalPrice);
    }


}
