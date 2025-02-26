package com.yaourt.exception.generic.endpoint;

import com.yaourt.exception.generic.AbstractCustomException;

public class Exception_Endpoint_NotInApiWhiteList extends AbstractCustomException {

    // -- CONSTRUCTOR --------------------------------------------------------------------------------------------------

    public Exception_Endpoint_NotInApiWhiteList(Throwable cause) {

        super(cause);

    }

    public Exception_Endpoint_NotInApiWhiteList(String message) {

        super(message);

    }

    public Exception_Endpoint_NotInApiWhiteList() {

        super("Api key is not valid in client list");

    }


}