package com.onetranslate.service.yaourt;

import com.onetranslate.service.family.FamilyService;
import com.onetranslate.service.family.model.dto.FamilyDtoRES;
import com.onetranslate.service.yaourt.model.dto.YaourtComputeDtoREQ;
import com.onetranslate.service.yaourt.model.dto.YaourtComputeDtoRES;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
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
                DayOfWeek.MONDAY, familyDtoRES.getConsummationHistoricMonday(),
                DayOfWeek.TUESDAY, familyDtoRES.getConsummationHistoricTuesday(),
                DayOfWeek.WEDNESDAY, familyDtoRES.getConsummationHistoricWednesday(),
                DayOfWeek.THURSDAY, familyDtoRES.getConsummationHistoricThursday(),
                DayOfWeek.FRIDAY, familyDtoRES.getConsummationHistoricFriday(),
                DayOfWeek.SATURDAY, familyDtoRES.getConsummationHistoricSaturday(),
                DayOfWeek.SUNDAY, familyDtoRES.getConsummationHistoricSunday());

        // -- To Instant (ISO 8601)
        Instant instant = Instant.parse(yaourtComputeDtoREQ.getDateBegin());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate startDate = localDateTime.toLocalDate();
        LocalDate endDate = localDateTime.with(TemporalAdjusters.lastDayOfYear()).toLocalDate();

        // -- Init
        int totalConsumption = 0;
        LocalDate currentDate = startDate;

        // -- Loop day of week
        while (!currentDate.toString().equals(endDate.toString())) {

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
