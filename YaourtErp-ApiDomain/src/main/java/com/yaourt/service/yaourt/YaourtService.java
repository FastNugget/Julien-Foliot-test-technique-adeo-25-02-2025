package com.yaourt.service.yaourt;

import com.yaourt.service.family.FamilyService;
import com.yaourt.service.family.model.dto.FamilyDtoREQ;
import com.yaourt.service.family.model.dto.FamilyDtoRES;
import com.yaourt.service.stock.StockService;
import com.yaourt.service.stock.model.dto.StockDtoREQ;
import com.yaourt.service.stock.model.dto.StockDtoRES;
import com.yaourt.service.yaourt.model.dto.YaourtComputeDtoREQ;
import com.yaourt.service.yaourt.model.dto.YaourtComputeDtoRES;
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
    private final StockService stockService;

    // -- MISC
    private volatile boolean isInit = false;


    // -- LIFECYCLE ----------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {

        // -- Init
        this.initAppData();

    }


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public YaourtComputeDtoRES computeYaourtConsumationByYearForFamily(String familyId, YaourtComputeDtoREQ yaourtComputeDtoREQ){

        System.out.println("DAT RECEIVE="+ yaourtComputeDtoREQ.getDateBegin());
        // -- Init
        YaourtComputeDtoRES yaourtComputeDtoRES = new YaourtComputeDtoRES();

        // -- Check
        FamilyDtoRES familyDtoRES = this.familyService.getFamily(familyId);
        StockDtoRES stockDtoRES = this.stockService.getStock("1");

        // -- Compute
        Map<DayOfWeek, Integer> YAOURT_CONSUMPTION
            = Map.of(
                DayOfWeek.MONDAY, familyDtoRES.getConsumptionHistoricMonday(),
                DayOfWeek.TUESDAY, familyDtoRES.getConsumptionHistoricTuesday(),
                DayOfWeek.WEDNESDAY, familyDtoRES.getConsumptionHistoricWednesday(),
                DayOfWeek.THURSDAY, familyDtoRES.getConsumptionHistoricThursday(),
                DayOfWeek.FRIDAY, familyDtoRES.getConsumptionHistoricFriday(),
                DayOfWeek.SATURDAY, familyDtoRES.getConsumptionHistoricSaturday(),
                DayOfWeek.SUNDAY, familyDtoRES.getConsumptionHistoricSunday());

        // -- To Instant (ISO 8601)
        Instant instant = Instant.parse(yaourtComputeDtoREQ.getDateBegin());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate startDate = localDateTime.toLocalDate();
        LocalDate endDate = localDateTime.with(TemporalAdjusters.lastDayOfYear()).toLocalDate();

        // -- Check delivery time
        startDate = startDate.minusDays(stockDtoRES.getDeliveryDelay());

        // -- Init
        int totalConsumption = 0;
        LocalDate currentDate = startDate;

        // -- Loop day of week
        while (!currentDate.toString().equals(endDate.toString())) {

            // -- Add
            totalConsumption += YAOURT_CONSUMPTION.getOrDefault(currentDate.getDayOfWeek(), 0);

            // -- Add
            yaourtComputeDtoRES.getDailyConsumptionList()
                    .add(new YaourtComputeDtoRES.DailyConsumption(
                            currentDate.toString(),
                            YAOURT_CONSUMPTION.getOrDefault(currentDate.getDayOfWeek(), 0)));

            // -- Shift
            currentDate = currentDate.plusDays(1);

        }


        // -- Build
        yaourtComputeDtoRES.setYaourtNumber(totalConsumption);

        // -- Commit
        return yaourtComputeDtoRES;

    }


    // -- MISC ---------------------------------------------------------------------------------------------------------

    public void initAppData(){

        // -- Check
        if(this.isInit) return;

        // -- Set family

        // -- Init
        FamilyDtoREQ familyDtoREQa = new FamilyDtoREQ();
        familyDtoREQa.setConsumptionHistoricMonday(3);
        familyDtoREQa.setConsumptionHistoricTuesday(3);
        familyDtoREQa.setConsumptionHistoricWednesday(3);
        familyDtoREQa.setConsumptionHistoricThursday(3);
        familyDtoREQa.setConsumptionHistoricFriday(3);
        familyDtoREQa.setConsumptionHistoricSaturday(4);
        familyDtoREQa.setConsumptionHistoricSunday(4);

        // -- Call
        FamilyDtoRES familyDtoRESa = this.familyService.createFamily(familyDtoREQa);


        // -- Set stock

        // -- Init
        StockDtoREQ stockDtoREQa = new StockDtoREQ();
        stockDtoREQa.setProductName("yaourt");
        stockDtoREQa.setQuantity(6);
        stockDtoREQa.setQuantityMultiple(2);
        stockDtoREQa.setDeliveryDelay(2);

        // -- Call
        StockDtoRES stockDtoRESa = this.stockService.createStock(stockDtoREQa);

        // -- Set
        this.isInit = true;

    }

}
