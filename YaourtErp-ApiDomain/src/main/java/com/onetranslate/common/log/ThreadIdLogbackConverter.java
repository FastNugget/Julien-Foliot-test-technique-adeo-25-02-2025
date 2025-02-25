package com.onetranslate.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ThreadIdLogbackConverter extends ClassicConverter {

    @Override
    public String convert(final ILoggingEvent iLoggingEvent) {

        // -- Compute and return
        return String.valueOf(Thread.currentThread().getId());

    }

}