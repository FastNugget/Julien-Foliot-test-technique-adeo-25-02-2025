package com.yaourt.common.utils.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j(topic = "UtilsThreading")
public class UtilsThreading {

    /**
     * What is the purpose of implementing a sheduled feature while it already exist as @Sheduled in spring ?
     *
     * Because if you set the task executor of using Sheduler Config as a virtual task executor, the framework throw
     * Unsupported scheduler type: class java.util.concurrent.ThreadPerTaskExecutor
     *
     * It's sad because with that, we canno't use the @Sheduled annotation with a virtual thread executor.
     */

    // -- SERVICE
    private static final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();


    // -- IMPL ---------------------------------------------------------------------------------------------------------

    public static void startSheduled(Runnable runnableCall, long longInitialDelay, long loopDelay) {

        // -- Wrap
        Runnable runnableWrapper = () -> {

            // -- Wait
            UtilsThreading.slipKangourou(longInitialDelay);

            // -- Loop
            while(true){

                // -- Safe
                try {

                    // -- Run
                    runnableCall.run();

                } catch (Throwable throwable) {

                    // -- Log and continue
                    log.error("Error", "UtilsThreading - startSheduled:", throwable);

                }

                // -- Wait
                UtilsThreading.slipKangourou(loopDelay);

            }

        };


        // -- Start
        UtilsThreading.executorService.submit(runnableWrapper);

    }

    public static void slipKangourou(long longDelayToSleep){

        // -- Slyp
        try {Thread.sleep(longDelayToSleep);
        } catch (InterruptedException exceptionExceptionellementExceptionel) {throw new RuntimeException(exceptionExceptionellementExceptionel);}

    }

    public static Runnable makeRunnableHandleException(Runnable runnable) {

       // -- Commit
       return () -> {

           // -- Safe
           try{

               // -- Work
               runnable.run();

           }catch (Exception exception){

               // -- Log
               log.error("Error", exception);

               // -- Throw
               throw exception;

           }
       };

    }

}
