package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yaourt.MainApplication;
import com.yaourt.service.family.FamilyService;
import com.yaourt.service.family.model.dto.FamilyDtoREQ;
import com.yaourt.service.family.model.dto.FamilyDtoRES;
import com.yaourt.service.stock.StockService;
import com.yaourt.service.stock.model.dto.StockDtoREQ;
import com.yaourt.service.stock.model.dto.StockDtoRES;
import com.yaourt.service.yaourt.YaourtController;
import com.yaourt.service.yaourt.model.dto.YaourtComputeDtoREQ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import utils.TestUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yaml")
@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith(SystemStubsExtension.class)
public class TestYaourtController {

    // -- REFS
    @Autowired
    private MockMvc mockMvc;

    @Autowired private FamilyService familyService;
    @Autowired private StockService stockService;
    @Autowired private YaourtController yaourtController;

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

        assertThat(this.yaourtController).isNotNull();

    }

    // -- TEST SERVICE -------------------------------------------------------------------------------------------------

    @Test
    public void test_Api_Calls() throws Exception {


        // -- Set family

        // -- Init
        Gson gson = new Gson();

        // -- Call
        FamilyDtoREQ familyDtoREQa = new FamilyDtoREQ();
        familyDtoREQa.setConsummationHistoricMonday(1);
        familyDtoREQa.setConsummationHistoricTuesday(2);
        familyDtoREQa.setConsummationHistoricWednesday(1);
        familyDtoREQa.setConsummationHistoricThursday(4);
        familyDtoREQa.setConsummationHistoricFriday(9);
        familyDtoREQa.setConsummationHistoricSaturday(6);
        familyDtoREQa.setConsummationHistoricSunday(2);

        FamilyDtoRES familyDtoRES = this.familyService.createFamily(familyDtoREQa);
        assertThat(familyDtoRES).isNotNull();


        // -- Set stock

        // -- Call
        StockDtoREQ stockDtoREQa = new StockDtoREQ();
        stockDtoREQa.setProductName("yaourt");
        stockDtoREQa.setQuantity(2);
        stockDtoREQa.setQuantityMultiple(2);
        stockDtoREQa.setDeliveryDelay(2);

        StockDtoRES stockDtoRES = this.stockService.createStock(stockDtoREQa);
        assertThat(stockDtoRES).isNotNull();

        // -- Call
        String id = familyDtoRES.getId();
        YaourtComputeDtoREQ yaourtComputeDtoREQ = new YaourtComputeDtoREQ();
        yaourtComputeDtoREQ.setDateBegin("2025-01-06T09:30:00Z");

        MvcResult mvcResulta
                = this.mockMvc.perform(get(String.format("/api/v1/yaourt/compute/%s", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(yaourtComputeDtoREQ)))
                .andExpect(status().isOk())
                .andReturn();


    }

}