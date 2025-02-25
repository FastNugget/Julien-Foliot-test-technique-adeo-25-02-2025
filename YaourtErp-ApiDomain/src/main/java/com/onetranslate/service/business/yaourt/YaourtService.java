package com.onetranslate.service.business.yaourt;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "YaourtService")
@RequiredArgsConstructor
@Service()
public class YaourtService {

    // -- VARS
    public final int intLivraisonDelay = 2;


    // -- LIFECYCLE ----------------------------------------------------------------------------------------------------

    @PostConstruct
    public void init() {}


    // -- CALLBACKS ----------------------------------------------------------------------------------------------------


}
