package umc.ShowHoo.web.rentalFee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.web.peakSeasonRentalFee.entity.PeakSeasonRentalFee;
import umc.ShowHoo.web.peakSeasonRentalFee.repository.PeakSeasonRentalFeeRepository;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.rentalFee.repository.RentalFeeRepository;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;

import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceAdditionalService.repository.SpaceAdditionalServiceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RentalFeeService {
    private final RentalFeeRepository rentalFeeRepository;
    private final PeakSeasonRentalFeeRepository peakSeasonRentalFeeRepository;
    private final SpaceAdditionalServiceRepository spaceAdditionalServiceRepository;


    public SpaceResponseDTO.SpacePriceResponseDTO getSpaceDate(Long spaceId, LocalDate date, List<String> additionalServices) {
        java.time.DayOfWeek javaDayOfWeek = date.getDayOfWeek();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(javaDayOfWeek.name());

        // 성수기 체크
        Set<Integer> peakSeasonMonths = Set.of(8, 11, 12);
        boolean isPeakSeason = peakSeasonMonths.contains(date.getMonthValue());

        int basePrice;

        if (isPeakSeason) {
            // 성수기일때
            PeakSeasonRentalFee peakSeasonRentalFee = peakSeasonRentalFeeRepository.findBySpaceIdAndDayOfWeek(spaceId, dayOfWeek);
            basePrice = peakSeasonRentalFee != null ? peakSeasonRentalFee.getFee() : 0;
        } else {
            // 비성수기일때
            RentalFee rentalFee = rentalFeeRepository.findBySpaceIdAndDayOfWeek(spaceId, dayOfWeek);
            basePrice = rentalFee != null ? rentalFee.getFee() : 0;
        }

        // 추가서비스 가격 계산
        int additionalServicePrice = 0;
        if (additionalServices != null) {
            for (String serviceTitle : additionalServices) {
                SpaceAdditionalService service = spaceAdditionalServiceRepository.findBySpaceIdAndTitle(spaceId, serviceTitle);
                if (service != null) {
                    additionalServicePrice += Integer.parseInt(service.getPrice());
                }
            }
        }

        int totalPrice = basePrice + additionalServicePrice;

        return new SpaceResponseDTO.SpacePriceResponseDTO(basePrice, additionalServicePrice, totalPrice);
    }


}
