package com.yaourt.service.family;

import com.yaourt.service.family.model.FamilyDao;
import com.yaourt.service.family.model.FamilyRepository;
import com.yaourt.service.family.model.dto.FamilyDtoREQ;
import com.yaourt.service.family.model.dto.FamilyDtoRES;
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

        // -- Create
        return this.modelMapper.map(this.familyRepository.save(this.modelMapper.map(familyDtoREQ, FamilyDao.class)), FamilyDtoRES.class);

    }


    public FamilyDtoRES getFamily(String familyId){

        // -- Get
        return this.modelMapper.map(this.familyRepository.findById(Long.valueOf(familyId)), FamilyDtoRES.class);

    }


    public FamilyDtoRES updateFamily(FamilyDtoREQ familyDtoREQ, String id){

        // -- Init
        FamilyDao familyDao = new FamilyDao();
        familyDao.setId(Long.valueOf(id));

        // -- Map data
        this.modelMapper.map(familyDtoREQ,familyDao);

        // -- Get
        return this.modelMapper.map(this.familyRepository.save(familyDao), FamilyDtoRES.class);

    }


}