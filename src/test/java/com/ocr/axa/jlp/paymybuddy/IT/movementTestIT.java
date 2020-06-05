package com.ocr.axa.jlp.paymybuddy.IT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
class movementTestIT {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 
     * Test : creation a movement of transfer
     */
    @Test
    public void createTransferTest() throws Exception {

        // GIVEN

        String questionBody = "{" + "\"account\":{\"user\":{\"email\": \"usertest@test.com\"}}," + "\"amount\": 1.00,"
                + " \"comment\": \"transfert user to buddy\"," + " \"user\":{\"email\":\"buddytest@test.com\"}" + "}";
        
        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/movement/payment").content(questionBody).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

    }
}
