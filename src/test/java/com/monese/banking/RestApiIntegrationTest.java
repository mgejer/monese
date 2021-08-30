package com.monese.banking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RestApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAccount() throws Exception {
        mockMvc.perform(get("/accounts/42")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
