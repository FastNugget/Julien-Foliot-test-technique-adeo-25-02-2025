package com.yaourt.service.family.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyDtoRES {

    // -- VARS
    private String id;

    private int consummationHistoricMonday;
    private int consummationHistoricTuesday;
    private int consummationHistoricWednesday;
    private int consummationHistoricThursday;
    private int consummationHistoricFriday;
    private int consummationHistoricSaturday;
    private int consummationHistoricSunday;

}
