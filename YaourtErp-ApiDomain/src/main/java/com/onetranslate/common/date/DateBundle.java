package com.onetranslate.common.date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onetranslate.common.utils.UtilsDate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DateBundle {

    // -- REFS
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'hh:mm:ss.SSS'Z'")
    private Date timestampUtc;
    private Long timeStamp;


    // -- CONSTRUCTOR --------------------------------------------------------------------------------------------------

    public DateBundle(boolean booleanShouldSetDateNow) {

        // -- Check
        if (booleanShouldSetDateNow == true) {

            // -- Cawl
            this.setDate();

        }

    }

    public DateBundle() {

        // -- Cawl
        this.setDate();

    }


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public void setDate(){

        this.timeStamp = UtilsDate.getTimestampFromInstantNow();
        this.timestampUtc = new Date(this.timeStamp);

    }

}
