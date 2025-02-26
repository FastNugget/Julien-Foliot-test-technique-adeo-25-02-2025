package com.yaourt.exception;

import com.yaourt.exception.generic.generic.Exception_Generic;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;


public enum ErrorModelEnum {

    // -- DEFAULT
    EXCEPTION_GENERIC("ERR-000001", HttpStatus.INTERNAL_SERVER_ERROR.value() , HttpStatus.INTERNAL_SERVER_ERROR, Exception_Generic.class),

    // -- CUSTOM
    EXCEPTION_TRANSLATION_RANDOM("ERR-000002", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, Exception.class);

    // -- VARS STATUS
    public final String errorCodeApplication;
    public final int errorCodeHttp;
    public final HttpStatus errorStatusHttp;

    public final List<Class<? extends Exception>> listClassLegacyException;


    // -- CONSTRUCTOR --------------------------------------------------------------------------------------------------

    @SafeVarargs
    ErrorModelEnum(String errorCodeApplication, int errorCodeHttp, HttpStatus errorStatusHttp, Class<? extends Exception>... arrayClassLegacyException){

        // -- Foock lombok which doesn't work on enum damn
        this.errorCodeApplication = errorCodeApplication;

        this.errorCodeHttp = errorCodeHttp;
        this.errorStatusHttp = errorStatusHttp;

        this.listClassLegacyException = Arrays.asList(arrayClassLegacyException);

    }

}
