package com.yaourt.service.yaourt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class YaourtComputeDtoRES {

    private int yaourtNumber;
    private List<DailyConsumption> dailyConsumptionList = new ArrayList<>();

    // -- CLASS --------------------------------------------------------------------------------------------------------
    @Getter
    @Setter
    @AllArgsConstructor
    public static class DailyConsumption {

        private String date;
        private int consumption;

    }

}
