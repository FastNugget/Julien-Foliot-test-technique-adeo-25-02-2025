package com.onetranslate.exception.generic;

import com.onetranslate.common.utils.UtilsSerial;
import lombok.Getter;

@Getter
public abstract class AbstractCustomException extends RuntimeException{

    // -- VARS
    public final String exceptionUuid = UtilsSerial.getSerialStringWithBackendSeed();


    // -- CONSTRUCTOR --------------------------------------------------------------------------------------------------

    public AbstractCustomException(Throwable throwableCause) {

        super(throwableCause);

    }

    public AbstractCustomException(String stringMessage, Throwable throwableCause) {

        super(stringMessage, throwableCause);

    }

    public AbstractCustomException(String stringMessage) {

        super(stringMessage);

    }



}
