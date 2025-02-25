package com.onetranslate.service.business.family.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("Family")
public class FamilyDao {

    // -- Mongoid
    @Id private String id;
    private YaourtConsumationHistoric yaourtConsumationHistoric;


    // -- CLASS --------------------------------------------------------------------------------------------------------

    @Getter @AllArgsConstructor
    public static class YaourtConsumationHistoric{

        // -- VARS
        private int monday;
        private int tuesday;
        private int wednesday;
        private int thursday;
        private int friday;
        private int saturday;
        private int sunday;


        // -- CALLBACKS -------------------------------------------------------

        public int getConsumationForDay(int day){

            // -- Fetch & Commit
            switch (day){

                case 0 : return this.monday;
                case 1 : return this.tuesday;
                case 2 : return this.wednesday;
                case 3 : return this.thursday;
                case 4 : return this.friday;
                case 5 : return this.saturday;
                case 6 : return this.sunday;
                default: throw new RuntimeException("Invalid day");

            }

        }

    }

}