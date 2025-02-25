package com.onetranslate.exception.dto;

import com.onetranslate.common.date.DateBundle;
import com.onetranslate.exception.ErrorModelEnum;
import com.onetranslate.exception.generic.AbstractCustomException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GlobalExceptionDto_Output {

    // -- VARS GLOBAL
    private DateBundle date;

    // -- VARS APPLICATION
    private String errorCode;
    private String errorUuid;
    private String errorMessage;

    // -- VARS HTTP
    private int errorCodeHttp;
    private HttpStatus errorStatusHttp;

    // -- VARS RUNTIME
    private String errorStackRuntime;

    // -- Potential data
    private Object data;


    // -- CONSTRUCTOR --------------------------------------------------------------------------------------------------

    public GlobalExceptionDto_Output(Exception ref_Exception, ErrorModelEnum ref_ErrorModelEnum, List<String> listStringActiveProfile) {

        // -- Set timestamp
        this.date = new DateBundle(true);

        // -- Set UUID
        if(ref_Exception instanceof AbstractCustomException){

            // -- Set
            this.errorUuid = ((AbstractCustomException) ref_Exception).getExceptionUuid();

        }

        // -- Init stack trace
        StringWriter ref_StringWriter = new StringWriter();
        ref_Exception.printStackTrace(new PrintWriter(ref_StringWriter));
        String ref_String_FullStackTrace = ref_StringWriter.toString();

        // -- Set runtime exception log
        if(listStringActiveProfile.contains("dev") || listStringActiveProfile.contains("dev-docker")){this.errorStackRuntime = ref_String_FullStackTrace;}


        // -- Switch
        switch(ref_Exception.getClass().getSimpleName()){

            case "MethodArgumentNotValidException":

                // -- Cast
                MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException)ref_Exception;

                // -- Set data
                this.errorMessage
                        = Arrays.stream(methodArgumentNotValidException.getDetailMessageArguments())
                        .map(a -> a.toString())
                        .filter(a -> !a.isBlank())
                        .collect(Collectors.joining(","));

                break;

            default: this.errorMessage = ref_Exception.getMessage();

        }

        // -- Set common data
        this.errorCode = ref_ErrorModelEnum.errorCodeApplication;
        this.errorCodeHttp = ref_ErrorModelEnum.errorCodeHttp;
        this.errorStatusHttp = ref_ErrorModelEnum.errorStatusHttp;

    }

}
