package com.onetranslate.service.common.mongodb;

import com.onetranslate.common.utils.thread.UtilsThreading;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "MongoDbService")
public class MongoDbService {

    // -- VARS
    private final MongoTemplate mongoTemplate;
    private final MongoTransactionManager mongoTransactionManager;
    private static final int intMongoTransactionMaxRetryNumber = 10;
    private static final int intMongoTransactionMaxDelayRetry = 3000;
    private final ReentrantLock reentrantLock = new ReentrantLock();


    // -- INITIALISATOR ------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init(){

        // -- Set template
        this.mongoTemplate.setSessionSynchronization(SessionSynchronization.ALWAYS);

    }


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public void executeTransaction(MongoDbTransactionWrapper mongoDbTransactionWrapper){

        /**
         * For the moment we didn't implement retry, but we have prepared the fields in case of
         */

        // -- Init
        TransactionTemplate transactionTemplate = new TransactionTemplate(this.mongoTransactionManager);
        Exception exceptionThrow = null;
        int offsetRetry = 0;

        // -- Loop retry for write conflict
        for(; offsetRetry < MongoDbService.intMongoTransactionMaxRetryNumber; offsetRetry++) {

            // -- Safe
            try{

                // -- Lock
                this.reentrantLock.lock();

                // -- Reset
                exceptionThrow = null;

                // -- Exec
                mongoDbTransactionWrapper
                        .runTransaction(this.mongoTemplate,
                                this.mongoTransactionManager,
                                MongoDbService.intMongoTransactionMaxRetryNumber,
                                MongoDbService.intMongoTransactionMaxDelayRetry,
                                transactionTemplate);

                // -- Stop
                break;

            }catch (Exception exception){

                // -- Set
                exceptionThrow = exception;

                // -- Unlock
                this.reentrantLock.unlock();

                // -- Sleep
                UtilsThreading.slipKangourou((long)(MongoDbService.intMongoTransactionMaxDelayRetry * Math.random()));

            }finally {

                // -- Unlock
                if(this.reentrantLock.isLocked()){this.reentrantLock.unlock();}

            }

        }

        if(exceptionThrow != null){

            // -- Log
            log.error("MongoDbService--executeTransaction: Error after " + (offsetRetry+1) + " retries: " + exceptionThrow.getMessage());

        }

    }

    // -- CLASS --------------------------------------------------------------------------------------------------------

    @FunctionalInterface
    public interface MongoDbTransactionWrapper{

        void runTransaction(MongoTemplate mongoTemplate, MongoTransactionManager mongoTransactionManager, int intMongoTransactionMaxRetryNumber, int intMongoTransactionMaxDelayRetry, TransactionTemplate transactionTemplate);

    }

}
