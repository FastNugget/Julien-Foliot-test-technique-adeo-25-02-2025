package com.onetranslate.common.utils;

import com.onetranslate.common.utils.thread.UtilsThreading;
import lombok.Getter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class UtilsSerial {

    /**
     * We added lock to avoid 2 threads having the same serial, in some case a 2 call to UUID return the same uuid
     * In this case, the method check it, and if it's the same, it juste discard it, wait 1ms, and retry
     */

    // -- VARS
    @Getter
    private static final String stringAppInstanceSeed = UUID.randomUUID().toString() + "-bs-" + UtilsDate.get_DateStringUtc_FromComputerClock();

    // -- SERIAL UTILS
    public static final Object objectLockLastSerial = new Object();
    private static volatile String stringLastSerial;

    public static final Object objectLockLastSerialFormatted = new Object();
    private static volatile String stringLastSerialFormatted;

    private static final Base64.Encoder base64Encoder = Base64.getEncoder();


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public static String getSerialString(){

        // -- Loop
        while(true){

            // -- Generate
            String stringSerialReceieve = UUID.randomUUID().toString();

            // -- Safe cache
            synchronized (objectLockLastSerial){

                // -- Check
                if(stringLastSerial == null || stringSerialReceieve.equals(stringLastSerial) == false){

                    // -- Set
                    stringLastSerial = stringSerialReceieve;

                    // -- Return
                    return stringSerialReceieve;

                }

            }

            // -- Wait little bit
            UtilsThreading.slipKangourou(1);

        }


    }

    public static String getSerialStringFormatted(){

        // -- Loop
        while(true){

            // -- Generate
            String stringSerialFormattedReceieve = Arrays.stream(UUID.randomUUID().toString().split("-")).collect(Collectors.joining()).toLowerCase(Locale.ROOT);

            // -- Safe cache
            synchronized (objectLockLastSerialFormatted){

                // -- Check
                if(stringLastSerialFormatted == null || stringSerialFormattedReceieve.equals(stringLastSerialFormatted) == false){

                    // -- Set
                    stringLastSerialFormatted = stringSerialFormattedReceieve;

                    // -- Return
                    return stringSerialFormattedReceieve;

                }

            }

            // -- Wait little bit
            UtilsThreading.slipKangourou(1);

        }


    }

    public static String getSerialStringWithBackendSeed(){

        // -- Loop
        while(true){

            // -- Generate
            String stringSerialReceieve = UUID.randomUUID().toString();

            // -- Safe cache
            synchronized (objectLockLastSerial){

                // -- Check
                if(stringLastSerial == null || stringSerialReceieve.equals(stringLastSerial) == false){

                    // -- Set
                    stringLastSerial = stringSerialReceieve;

                    // -- Return
                    return stringSerialReceieve + UtilsSerial.stringAppInstanceSeed;

                }

            }

            // -- Wait little bit
            UtilsThreading.slipKangourou(1);

        }


    }


    // -- UTILS --------------------------------------------------------------------------------------------------------

    private static KeyPair getKeyPair() throws NoSuchAlgorithmException {

        // -- Init
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);

        // -- Commit
        return keyPairGenerator.genKeyPair();

    }

}
