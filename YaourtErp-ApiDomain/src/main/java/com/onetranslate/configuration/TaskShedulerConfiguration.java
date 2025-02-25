package com.onetranslate.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.task.ThreadPoolTaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Slf4j(topic = "TaskShedulerConfiguration")
@Configuration
public class TaskShedulerConfiguration {

    // -- VARS
    @Value("${spring.task.scheduling.pool.size}") public int taskSchedulerPoolSize;

    // -- IMPL ---------------------------------------------------------------------------------------------------------

    @Bean public ThreadPoolTaskSchedulerCustomizer taskSchedulerCustomizer() {

        // -- Set & Commit
        return ref_TaskSchedulerCustomizer ->

                ref_TaskSchedulerCustomizer.setErrorHandler(ref_Throwable -> {

                    // -- Log
                    log.error(Thread.currentThread().getName(), ref_Throwable);

                });

    }

    @Bean public ThreadPoolTaskScheduler taskScheduler(){

        /**
         *
         * There is a issue with TaskSheduler and virtual threading
         * Springboot TaskSheduler use ThreadPool that doesn't support virtual thread
         * See here discussion https://github.com/spring-projects/spring-boot/issues/35710
         *
         * Is it intended to work with @Sheduled annotation, those task are not virtual, they doesn't need to be
         * Virtual is used only for scaling tasks
         */

        // -- Set
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(this.taskSchedulerPoolSize);

        /**
         * Disabled be cause we let normal platform thread to handle those recurrent task
         * We only use virtual thread for scalability
         */
        // threadPoolTaskScheduler.setThreadFactory(Thread.ofVirtual().factory());

        // -- Return
        return threadPoolTaskScheduler;

    }

}