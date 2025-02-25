package com.onetranslate.service.business.stock;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "StockController")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class StockController {

    // -- VARS


    // -- INITIALISATOR ------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init(){}


    // -- CONTROLLERS --------------------------------------------------------------------------------------------------

    @PatchMapping(value = "/stock/{id}/delivery/delay", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> update(@RequestBody @Valid String body){

        // -- Work
        throw new RuntimeException("Not implemented");
    }

}