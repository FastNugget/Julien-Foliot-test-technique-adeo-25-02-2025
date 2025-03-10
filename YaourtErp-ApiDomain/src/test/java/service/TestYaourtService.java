package service;

import com.yaourt.MainApplication;
import com.yaourt.service.family.FamilyService;
import com.yaourt.service.family.model.dto.FamilyDtoREQ;
import com.yaourt.service.family.model.dto.FamilyDtoRES;
import com.yaourt.service.stock.StockService;
import com.yaourt.service.stock.model.dto.StockDtoREQ;
import com.yaourt.service.stock.model.dto.StockDtoRES;
import com.yaourt.service.yaourt.YaourtService;
import com.yaourt.service.yaourt.model.dto.YaourtComputeDtoREQ;
import com.yaourt.service.yaourt.model.dto.YaourtComputeDtoRES;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import utils.TestUtils;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainApplication.class)
@TestPropertySource(locations = "classpath:application.yaml")
@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith(SystemStubsExtension.class)
public class TestYaourtService {

    // -- REFS
    @Autowired private FamilyService familyService;
    @Autowired private StockService stockService;
    @Autowired private YaourtService yaourtService;

    @SystemStub
    private static EnvironmentVariables environmentVariableHanlder = new EnvironmentVariables();


    // -- INIT --------------------------------------------------------------------------------------------------------

    @BeforeAll
    public static void setUpAll() throws IOException {

        // -- Set env
        TestUtils.setEnv(environmentVariableHanlder);

    }


    // -- TESTS --------------------------------------------------------------------------------------------------------

    @Test
    void test_api_call() {


        // -- Set family

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


        // -- Set stock

            // -- Init
            StockDtoREQ stockDtoREQa = new StockDtoREQ();
            stockDtoREQa.setProductName("yaourt");
            stockDtoREQa.setQuantity(2);
            stockDtoREQa.setQuantityMultiple(2);
            stockDtoREQa.setDeliveryDelay(2);

            // -- Call
            StockDtoRES stockDtoRESa = this.stockService.createStock(stockDtoREQa);


        // -- Test yaourt

            // -- Init
            YaourtComputeDtoREQ yaourtComputeDtoREQ = new YaourtComputeDtoREQ();
            yaourtComputeDtoREQ.setDateBegin("2025-01-06T09:30:00Z");

            // -- Call
            YaourtComputeDtoRES yaourtComputeDtoRES = this.yaourtService.computeYaourtConsumationByYearForFamily(familyDtoRESa.getId(), yaourtComputeDtoREQ);

            // -- Assert
            Assertions.assertNotNull(yaourtComputeDtoRES);


    }


}
