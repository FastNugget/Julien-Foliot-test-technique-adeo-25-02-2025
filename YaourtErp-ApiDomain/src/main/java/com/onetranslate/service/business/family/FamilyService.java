package com.onetranslate.service.business.family;

import com.onetranslate.service.business.family.model.FamilyDao;
import com.onetranslate.service.business.family.model.FamilyRepository;
import com.onetranslate.service.business.family.model.dto.FamilyDtoREQ;
import com.onetranslate.service.business.family.model.dto.FamilyDtoRES;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Slf4j(topic = "FamilyService")
@RequiredArgsConstructor
@Service()
public class FamilyService {

    // -- VARS
    private final FamilyRepository familyRepository;
    private ModelMapper modelMapper;

    // -- LIFECYCLE ----------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {

        // -- Set
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    }


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------

    public FamilyDtoRES createFamily(FamilyDtoREQ familyDtoREQ){

        // -- Get
        return this.modelMapper.map(this.familyRepository.save(this.modelMapper.map(familyDtoREQ, FamilyDao.class)), FamilyDtoRES.class);

    }

    public FamilyDtoRES updateFamily(FamilyDtoREQ familyDtoREQ, String id){

        // -- Init
        FamilyDao familyDao = new FamilyDao();
        familyDao.setId(id);

        // -- Map data
        this.modelMapper.map(familyDtoREQ,familyDao);

        // -- Get
        return this.modelMapper.map(this.familyRepository.save(familyDao), FamilyDtoRES.class);

    }


}