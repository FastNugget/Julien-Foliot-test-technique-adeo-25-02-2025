package com.onetranslate.exception.generic.db;

import com.onetranslate.common.utils.UtilsSerial;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class ExceptionForDatabase {

    // -- REFS DATA
    private List<String> listStackTrace;
    private String errorUuid = UtilsSerial.getSerialStringWithBackendSeed();
    private String exceptionMessage;
    private String exceptionInfo;

    @Setter(AccessLevel.NONE)
    private HashMap<String,String> metadata = new HashMap<>();

    private int httpStatusErrorCode;
    private String httpRequestUrl;
    private String httpRequestBody;
    private String httpStatusErrorResponse;
    private Object httpStatusErrorResponseBody;
    private List<ExceptionForDatabase> listSubExceptions = new CopyOnWriteArrayList<>();


    // -- UTILS --------------------------------------------------------------------------------------------------------

    public void setMetadata(String stringKey, String stringValue){

        // -- Add
        this.metadata.put(stringKey, stringValue);

    }

}