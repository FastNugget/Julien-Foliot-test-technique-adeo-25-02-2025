package com.yaourt.service.stock;

import com.yaourt.service.stock.model.dto.StockDtoREQ;
import com.yaourt.service.stock.model.dto.StockDtoRES;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "StockController")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class StockController {

    // -- VARS
    private final StockService stockService;

    // -- INITIALISATOR ------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init(){}


    // -- CONTROLLERS --------------------------------------------------------------------------------------------------

    @PostMapping(value = "/stock/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDtoRES> create(@RequestBody @Valid StockDtoREQ stockDtoREQ){

        // -- Work
        return ResponseEntity.ok(this.stockService.createStock(stockDtoREQ));

    }

    @PatchMapping(value = "/stock/{id}/delivery/delay", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDtoRES> update(@RequestBody @Valid StockDtoREQ stockDtoREQ, @PathVariable("id") String id){

        // -- Work
        return ResponseEntity.ok(this.stockService.updateDeliveryDelay(id, stockDtoREQ));

    }

    @GetMapping(value = "/stock/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDtoRES> get(@PathVariable("id") String id){

        // -- Work
        return ResponseEntity.ok(this.stockService.getStock(id));

    }


}