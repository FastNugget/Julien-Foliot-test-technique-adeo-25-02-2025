package com.onetranslate.service.business.yaourt;

import com.onetranslate.service.business.family.FamilyService;
import com.onetranslate.service.business.family.model.dto.FamilyDtoRES;
import com.onetranslate.service.business.yaourt.model.dto.YaourtComputeDtoREQ;
import com.onetranslate.service.business.yaourt.model.dto.YaourtComputeDtoRES;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Map;

@Slf4j(topic = "YaourtService")
@RequiredArgsConstructor
@Service()
public class YaourtService {

    // -- VARS
    private final FamilyService familyService;


    // -- LIFECYCLE ----------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {}


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public YaourtComputeDtoRES computeYaourtConsumationByYearForFamily(String familyId, YaourtComputeDtoREQ yaourtComputeDtoREQ){

        // -- Check
        FamilyDtoRES familyDtoRES = this.familyService.getFamily(familyId);

        // -- Compute
        Map<DayOfWeek, Integer> YAOURT_CONSUMPTION
            = Map.of(
                DayOfWeek.MONDAY, familyDtoRES.getYaourtConsumationHistoric().getMonday(),
                DayOfWeek.TUESDAY, familyDtoRES.getYaourtConsumationHistoric().getTuesday(),
                DayOfWeek.WEDNESDAY, familyDtoRES.getYaourtConsumationHistoric().getWednesday(),
                DayOfWeek.THURSDAY, familyDtoRES.getYaourtConsumationHistoric().getThursday(),
                DayOfWeek.FRIDAY, familyDtoRES.getYaourtConsumationHistoric().getFriday(),
                DayOfWeek.SATURDAY, familyDtoRES.getYaourtConsumationHistoric().getSaturday(),
                DayOfWeek.SUNDAY, familyDtoRES.getYaourtConsumationHistoric().getSunday());

        // -- To Instant
        Instant instant = Instant.parse(String.valueOf(yaourtComputeDtoREQ.getTimestampBegin()));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate startDate = localDateTime.toLocalDate();

        // -- Init
        int totalConsumption = 0;
        LocalDate currentDate = startDate;

        // -- Loop day of week
        while (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY.plus(1)) {

            // -- Add
            totalConsumption += YAOURT_CONSUMPTION.getOrDefault(currentDate.getDayOfWeek(), 0);

            // -- Shift
            currentDate = currentDate.plusDays(1);

        }

        // -- Build
        YaourtComputeDtoRES yaourtComputeDtoRES = new YaourtComputeDtoRES();
        yaourtComputeDtoRES.setYaourtNumber(totalConsumption);

        // -- Commit
        return yaourtComputeDtoRES;

    }


}
