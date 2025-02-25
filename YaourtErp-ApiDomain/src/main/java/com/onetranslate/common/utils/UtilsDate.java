package com.onetranslate.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class UtilsDate {

    // -- VARS
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static String stringDateUtcLocal_Null = "0000-00-00T00:00:00.000Z";


    // -- COWLBAQUE ----------------------------------------------------------------------------------------------------

    public static String get_DateStringUtc_FromComputerClock(){

        return simpleDateFormat.format(new Date(System.currentTimeMillis()));

    }


    public static String convert_DateToString(Date ref_Date){

        return simpleDateFormat.format(ref_Date);

    }

    public static Date convert_StringToDate(String ref_String_DateToParse){

        try {

            return simpleDateFormat.parse(ref_String_DateToParse);

        } catch (ParseException e) {throw new RuntimeException(e);}

    }


    public static int compare_DateString(String stringDate1, String stringDate2){

        return UtilsDate.convert_StringToDate(stringDate1).compareTo(UtilsDate.convert_StringToDate(stringDate2));

    }


    public static String addOrSub__TimeToDate(String stringDateToParse, int intAmount, ChronoUnit chronoUnit){

        // -- Init
        Date dateActual = UtilsDate.convert_StringToDate(stringDateToParse);

        // -- Build
        Instant instant
            = new Date(dateActual.getTime()).toInstant()
                .plus(intAmount,chronoUnit);

        // -- Build againe
        Date dateToReturn = Date.from(instant);

        // -- Return
        return simpleDateFormat.format(dateToReturn);

    }

    public static Long getTimestamp(){

        return System.currentTimeMillis();

    }

    public static Long getTimestampFromInstantNow(){

        return Instant.now().toEpochMilli();

    }
}
