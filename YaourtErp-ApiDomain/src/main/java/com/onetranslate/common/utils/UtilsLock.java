package com.onetranslate.common.utils;

import java.util.concurrent.locks.ReentrantLock;

public class UtilsLock {

    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public static void unlock_Properly(ReentrantLock reentrantLock){

        // -- Check
        if(reentrantLock.isHeldByCurrentThread()){

            // -- Do
            reentrantLock.unlock();

        }
    }

}
