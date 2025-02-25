package com.onetranslate.service.business.yaourt;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j(topic = "YaourtController")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class YaourtController {

    // -- VARS


    // -- INITIALISATOR ------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init(){}


    // -- CONTROLLERS --------------------------------------------------------------------------------------------------

    @GetMapping(value = "/yaourt/compute", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> compute(){

        // -- Work
        throw new RuntimeException("Not implemented");

    }


}
