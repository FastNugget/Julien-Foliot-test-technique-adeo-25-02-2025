package com.yaourt.configuration.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "SpringContextEventListener")
public class SpringContextEventListener {

    // -- VARS
    @Value("${spring.profiles.active}") private String stringActiveProfile;


    // -- CALLBACKS OUTER ----------------------------------------------------------------------------------------------

    @EventListener
    public void handleContextRefreshed(ContextRefreshedEvent contextRefreshedEvent) {

        // -- Do
        this.logEveryProperties_OnlyInProfile_Dev(contextRefreshedEvent);

    }


    // -- CALLBACKS INNER ----------------------------------------------------------------------------------------------

    /**
     *
     * It's only call when we are in DEV profile, don't worry about buddy
     *
     */
    public void logEveryProperties_OnlyInProfile_Dev(ContextRefreshedEvent contextRefreshedEvent){

        // -- Check
        if(this.stringActiveProfile != null && (this.stringActiveProfile.equals("dev") || this.stringActiveProfile.equals("dev-docker"))){

            /**
             * Do something usefyll for your project
             */

        }

    }

}
