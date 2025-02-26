package com.yaourt.exception.generic.endpoint;

import com.yaourt.exception.generic.AbstractCustomException;

public class Exception_Endpoint_ApiKeyNotValid extends AbstractCustomException {

    // -- CONSTRUCTOR --------------------------------------------------------------------------------------------------

    public Exception_Endpoint_ApiKeyNotValid(Throwable cause) {

        super(cause);

    }

    public Exception_Endpoint_ApiKeyNotValid(String message) {

        super(message);

    }
}