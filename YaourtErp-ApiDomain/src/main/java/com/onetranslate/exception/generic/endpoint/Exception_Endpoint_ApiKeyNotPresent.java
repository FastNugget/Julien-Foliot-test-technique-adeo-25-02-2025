package com.onetranslate.exception.generic.endpoint;

import com.onetranslate.exception.generic.AbstractCustomException;

public class Exception_Endpoint_ApiKeyNotPresent extends AbstractCustomException {

    // -- CONSTRUCTOR --------------------------------------------------------------------------------------------------

    public Exception_Endpoint_ApiKeyNotPresent(Throwable cause) {

        super(cause);

    }

    public Exception_Endpoint_ApiKeyNotPresent(String message) {

        super(message);

    }

    public Exception_Endpoint_ApiKeyNotPresent() {

        super("Api key is not present");

    }



}
