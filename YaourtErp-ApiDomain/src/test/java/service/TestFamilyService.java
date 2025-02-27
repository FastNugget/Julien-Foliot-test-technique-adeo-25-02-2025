package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaourt.MainApplication;
import com.yaourt.service.family.FamilyService;
import com.yaourt.service.family.model.dto.FamilyDtoREQ;
import com.yaourt.service.family.model.dto.FamilyDtoRES;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import utils.TestUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainApplication.class)
@TestPropertySource(locations = "classpath:application.yaml")
@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith(SystemStubsExtension.class)
public class TestFamilyService {

    // -- REFS
    @Autowired
    private FamilyService familyService;

    // -- REFS MODEL TEST
    private ModelMapper modelMapper = new ModelMapper();
    private ObjectMapper objectMapper = new ObjectMapper();

    @SystemStub
    private static EnvironmentVariables environmentVariableHanlder = new EnvironmentVariables();


    // -- INIT ---------------------------------------------------------------------------------------------------------

    @BeforeAll
    public static void setUpAll() throws IOException {

        // -- Set env
        TestUtils.setEnv(environmentVariableHanlder);

    }


    // -- TESTS --------------------------------------------------------------------------------------------------------

    @Test
    void contextLoads() throws Exception {

        assertThat(this.familyService).isNotNull();

    }


    // -- TEST SERVICE -------------------------------------------------------------------------------------------------

    @Test
    public void test_Api_Calls() throws Exception {

        // -- Init
        FamilyDtoREQ familyDtoREQa = new FamilyDtoREQ();
        familyDtoREQa.setConsumptionHistoricMonday(1);
        familyDtoREQa.setConsumptionHistoricTuesday(2);
        familyDtoREQa.setConsumptionHistoricWednesday(1);
        familyDtoREQa.setConsumptionHistoricThursday(4);
        familyDtoREQa.setConsumptionHistoricFriday(9);
        familyDtoREQa.setConsumptionHistoricSaturday(6);
        familyDtoREQa.setConsumptionHistoricSunday(2);

        // -- Call
        FamilyDtoRES familyDtoRESa = this.familyService.createFamily(familyDtoREQa);

        // -- Assert
        Assertions.assertNotNull(familyDtoRESa);

        // -- Call
        FamilyDtoREQ familyDtoREQb = familyDtoREQa;
        FamilyDtoRES familyDtoRESb = this.familyService.updateFamily(familyDtoREQb, familyDtoRESa.getId());

        // -- Assert
        Assertions.assertNotNull(familyDtoRESb);

        // -- Call
        FamilyDtoREQ familyDtoREQc = familyDtoREQa;
        FamilyDtoRES familyDtoRESc = this.familyService.getFamily(familyDtoRESa.getId());

        // -- Assert
        Assertions.assertNotNull(familyDtoRESc);


    }

}
