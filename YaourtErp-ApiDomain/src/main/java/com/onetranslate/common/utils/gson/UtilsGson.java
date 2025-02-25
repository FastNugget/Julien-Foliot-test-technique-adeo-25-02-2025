package com.onetranslate.common.utils.gson;

public class UtilsGson {

    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public static String removeBackSlash(String stringPayload){

        // -- Work & Commit
        return stringPayload.replaceAll("\\\\", "");

    }

}
