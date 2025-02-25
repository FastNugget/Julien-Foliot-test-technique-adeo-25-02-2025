package com.onetranslate.service.business.family;

import com.onetranslate.service.business.family.model.FamilyDao;
import com.onetranslate.service.business.family.model.FamilyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "FamilyService")
@RequiredArgsConstructor
@Service()
public class FamilyService {

    // -- VARS
    private final FamilyRepository familyRepository;


    // -- LIFECYCLE ----------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {}


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public FamilyDao createFamily(FamilyDao familyDao){

        // -- Get
        return this.familyRepository.save(familyDao);

    }

    public FamilyDao getFamily(String id){

        // -- Call
        return this.familyRepository.findById(id).orElseThrow(() -> new RuntimeException("Family not found"));

    }

}