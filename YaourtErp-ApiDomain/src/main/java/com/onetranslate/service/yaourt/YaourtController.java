package com.onetranslate.service.yaourt;

import com.onetranslate.service.yaourt.model.dto.YaourtComputeDtoREQ;
import com.onetranslate.service.yaourt.model.dto.YaourtComputeDtoRES;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j(topic = "YaourtController")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class YaourtController {

    // -- VARS
    private final YaourtService yaourtService;


    // -- INITIALISATOR ------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init(){}


    // -- CONTROLLERS --------------------------------------------------------------------------------------------------

    @GetMapping(value = "/yaourt/compute/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<YaourtComputeDtoRES> compute(@RequestBody @Valid YaourtComputeDtoREQ yaourtComputeDtoREQ, @PathVariable("id") String familyId){

        // -- Work
       return ResponseEntity.ok(this.yaourtService.computeYaourtConsumationByYearForFamily(familyId, yaourtComputeDtoREQ));

    }

    // -- MISC ---------------------------------------------------------------------------------------------------------

    @GetMapping(value = "/init", produces = MediaType.APPLICATION_JSON_VALUE)
    public void compute(){

        // -- Work
        this.yaourtService.initAppData();

    }

}
