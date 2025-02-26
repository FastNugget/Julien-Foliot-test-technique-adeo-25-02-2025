package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yaourt.MainApplication;
import com.yaourt.service.family.FamilyController;
import com.yaourt.service.family.model.dto.FamilyDtoREQ;
import com.yaourt.service.family.model.dto.FamilyDtoRES;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MainApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yaml")
@TestMethodOrder(MethodOrderer.MethodName.class)
@ExtendWith(SystemStubsExtension.class)
public class TestFamilyController {

    // -- REFS
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FamilyController familyController;

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

        assertThat(this.familyController).isNotNull();

    }


    // -- TEST SERVICE -------------------------------------------------------------------------------------------------

    @Test
    public void test_Api_Calls() throws Exception {

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

        MvcResult mvcResulta
                = this.mockMvc.perform(post("/api/v1/family")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(familyDtoREQa)))
                .andExpect(status().isOk())
                .andReturn();

        // -- Call
        FamilyDtoREQ familyDtoREQb = familyDtoREQa;
        MvcResult mvcResultb
                = this.mockMvc.perform(patch(String.format("/api/v1/family/%s", gson.fromJson(mvcResulta.getResponse().getContentAsString(), FamilyDtoRES.class).getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(familyDtoREQb)))
                .andExpect(status().isOk())
                .andReturn();

        // -- Call
        FamilyDtoREQ familyDtoREQc = familyDtoREQb;
        MvcResult mvcResultc
                = this.mockMvc.perform(get(String.format("/api/v1/family/%s", gson.fromJson(mvcResulta.getResponse().getContentAsString(), FamilyDtoRES.class).getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(familyDtoREQc)))
                .andExpect(status().isOk())
                .andReturn();


    }


}
