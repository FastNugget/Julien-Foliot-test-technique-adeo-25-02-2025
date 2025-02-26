package com.onetranslate.service.family.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FamilyDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // -- VARS
    private int consummationHistoricMonday;
    private int consummationHistoricTuesday;
    private int consummationHistoricWednesday;
    private int consummationHistoricThursday;
    private int consummationHistoricFriday;
    private int consummationHistoricSaturday;
    private int consummationHistoricSunday;

}