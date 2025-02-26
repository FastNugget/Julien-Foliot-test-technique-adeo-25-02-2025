package com.onetranslate.service.family;

import com.onetranslate.service.family.model.dto.FamilyDtoREQ;
import com.onetranslate.service.family.model.dto.FamilyDtoRES;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "FamilyController")
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class FamilyController {

    // -- VARS
    private final FamilyService familyService;

    // -- INITIALISATOR ------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init(){}


    // -- CONTROLLERS --------------------------------------------------------------------------------------------------

    @PostMapping(value = "/family", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FamilyDtoRES> create(@RequestBody @Valid FamilyDtoREQ body){

        // -- Work
        return ResponseEntity.ok(this.familyService.createFamily(body));

    }

    @PatchMapping(value = "/family/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FamilyDtoRES> update(@RequestBody @Valid FamilyDtoREQ body, @PathVariable("id") String id){

        // -- Work
        return ResponseEntity.ok(this.familyService.updateFamily(body, id));
    }

    @GetMapping(value = "/family/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FamilyDtoRES> get(@PathVariable("id") String id){

        // -- Work
        return ResponseEntity.ok(this.familyService.getFamily(id));

    }

}