package com.yaourt.exception;

import com.yaourt.exception.generic.db.ExceptionForDatabase;
import com.yaourt.exception.dto.GlobalExceptionDto_Output;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j(topic = "GlobalExceptionHandler")
@Getter
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    // -- VARS
    final private Environment environment;
    private List<String> listStringActiveProfile;

    public static String STRING_LINESEPARATOR = "\r\n";


    // -- INITIALISATOR ------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init(){

        // -- Init
        this.listStringActiveProfile = Arrays.stream(this.environment.getActiveProfiles()).toList();

    }


    // -- IMPL ---------------------------------------------------------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalExceptionDto_Output> handleAllExceptions(Exception exception){

        // -- Print
        log.error(exception.getMessage(), exception);

        // -- Build & Commit
        return this.formatResponse(exception);

    }

    // -- SWITCH -------------------------------------------------------------------------------------------------------

    private ResponseEntity<GlobalExceptionDto_Output> formatResponse(Exception exception){

        // -- Map
        ErrorModelEnum errorModelEnum = this.fetchErrorModelException(exception);

        // -- Commit
        return new ResponseEntity<>(new GlobalExceptionDto_Output(exception, errorModelEnum, this.listStringActiveProfile), errorModelEnum.errorStatusHttp);

    }

    private ErrorModelEnum fetchErrorModelException(Exception exception){

        // -- Init
        ErrorModelEnum errorModelEnum = ErrorModelEnum.EXCEPTION_GENERIC;

        // -- Loop over annotation
        for(ErrorModelEnum errorModelEnumUnit : ErrorModelEnum.values()){

            // -- Loop over list class
            for(Class<? extends Exception> classLegacyUnit : errorModelEnumUnit.listClassLegacyException){

                // -- Check class correspondence
                if(exception.getClass().getName().equals(classLegacyUnit.getName())){

                    // -- Commit
                    return errorModelEnumUnit;

                }

            }

        }

        // -- Commit
        return errorModelEnum;

    }

    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public static ArrayList<String> dumpStack(Throwable throwableRoot){

        // -- Init
        ArrayList<String> arrayListToCommit = new ArrayList<>();

        // -- Work parent
        String[] arrayStringStackTrace = Arrays.stream(throwableRoot.getStackTrace())
                .map(traceElementUnit -> traceElementUnit.toString())
                .toArray(String[]::new);

        // -- Push
        arrayListToCommit
                .add(Arrays.stream(arrayStringStackTrace)
                        .collect(Collectors.joining(STRING_LINESEPARATOR)) + STRING_LINESEPARATOR);

        // -- Work Child
        Throwable ref_Throwable_Cause = throwableRoot.getCause();

        // -- Check
        if(ref_Throwable_Cause != null){

            // -- Boom
            arrayListToCommit.addAll(dumpStack(ref_Throwable_Cause));

        }

        // -- Commit
        return arrayListToCommit;

    }

    public static ExceptionForDatabase toPersistableException(Throwable throwableRoot){

        // -- Set
        ExceptionForDatabase exceptionForDatabase = new ExceptionForDatabase();
        exceptionForDatabase.setListStackTrace(GlobalExceptionHandler.dumpStack(throwableRoot));
        exceptionForDatabase.setExceptionMessage(throwableRoot.getMessage());

        // -- Commit
        return exceptionForDatabase;

    }
}