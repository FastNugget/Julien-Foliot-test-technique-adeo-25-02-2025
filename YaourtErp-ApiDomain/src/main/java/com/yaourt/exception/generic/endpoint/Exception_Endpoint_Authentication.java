package com.yaourt.exception.generic.endpoint;

import com.yaourt.exception.generic.AbstractCustomException;

public class Exception_Endpoint_Authentication extends AbstractCustomException {

    // -- CONSTRUCTOR --------------------------------------------------------------------------------------------------

    public Exception_Endpoint_Authentication(Throwable cause) {

        super(cause);

    }

    public Exception_Endpoint_Authentication(String message) {

        super(message);

    }
}