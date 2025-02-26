package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yaourt.MainApplication;
import com.yaourt.service.stock.StockService;
import com.yaourt.service.stock.model.dto.StockDtoREQ;
import com.yaourt.service.stock.model.dto.StockDtoRES;
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
public class TestStockService {

    // -- REFS
    @Autowired
    private StockService stockService;

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

        assertThat(this.stockService).isNotNull();

    }


    // -- TEST SERVICE -------------------------------------------------------------------------------------------------

    @Test
    public void test_Api_Calls() throws Exception {

        // -- Init
        Gson gson = new Gson();

        // -- Init
        StockDtoREQ stockDtoREQa = new StockDtoREQ();
        stockDtoREQa.setProductName("yaourt");
        stockDtoREQa.setQuantity(2);
        stockDtoREQa.setQuantityMultiple(2);
        stockDtoREQa.setDeliveryDelay(2);

        // -- Call
        StockDtoRES stockDtoRESa = this.stockService.createStock(stockDtoREQa);

        // -- Assert
        Assertions.assertNotNull(stockDtoRESa);

        // -- Init
        StockDtoREQ stockDtoREQb = this.modelMapper.map(stockDtoRESa, StockDtoREQ.class);
        stockDtoREQa.setDeliveryDelay(987);

        // -- Call
        StockDtoRES stockDtoRESb = this.stockService.updateDeliveryDelay(String.valueOf(stockDtoRESa.getId()), stockDtoREQb);

        // -- Assert
        Assertions.assertNotNull(stockDtoRESb);
        Assertions.assertEquals(stockDtoRESb.getDeliveryDelay(), stockDtoREQb.getDeliveryDelay());

    }

}
