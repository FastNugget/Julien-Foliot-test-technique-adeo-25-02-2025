package com.yaourt.service.family.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyDtoRES {

    // -- VARS
    private String id;

    private int consumptionHistoricMonday;
    private int consumptionHistoricTuesday;
    private int consumptionHistoricWednesday;
    private int consumptionHistoricThursday;
    private int consumptionHistoricFriday;
    private int consumptionHistoricSaturday;
    private int consumptionHistoricSunday;

}
