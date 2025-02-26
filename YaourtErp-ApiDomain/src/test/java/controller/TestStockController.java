package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yaourt.MainApplication;
import com.yaourt.service.stock.StockController;
import com.yaourt.service.stock.model.dto.StockDtoREQ;
import com.yaourt.service.stock.model.dto.StockDtoRES;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yaml")
@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith(SystemStubsExtension.class)
public class TestStockController {

    // -- REFS
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockController stockController;

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

        assertThat(this.stockController).isNotNull();

    }


    // -- TEST SERVICE -------------------------------------------------------------------------------------------------

    @Test
    public void test_Api_Calls() throws Exception {

        // -- Init
        Gson gson = new Gson();

        // -- Call
        StockDtoREQ stockDtoREQa = new StockDtoREQ();
        stockDtoREQa.setProductName("yaourt");
        stockDtoREQa.setQuantity(2);
        stockDtoREQa.setQuantityMultiple(2);
        stockDtoREQa.setDeliveryDelay(2);

        MvcResult mvcResulta
                = this.mockMvc.perform(post("/api/v1/stock/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockDtoREQa)))
                .andExpect(status().isOk())
                .andReturn();

        StockDtoRES stockDtoRES = this.modelMapper.map(mvcResulta.getResponse().getContentAsString(), StockDtoRES.class);

        // -- Call
        StockDtoREQ stockDtoREQb = this.modelMapper.map(stockDtoRES, StockDtoREQ.class);
        stockDtoREQa.setDeliveryDelay(987);

        MvcResult mvcResultb
                = this.mockMvc.perform(patch(String.format("/api/v1/stock/%s/delivery/delay", stockDtoRES.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockDtoREQb)))
                .andExpect(status().isOk())
                .andReturn();

    }

}