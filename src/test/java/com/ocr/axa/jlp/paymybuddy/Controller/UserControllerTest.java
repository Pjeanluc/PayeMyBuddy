package com.ocr.axa.jlp.paymybuddy.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ocr.axa.jlp.paymybuddy.model.Bank;
import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.service.ConnectionService;
import com.ocr.axa.jlp.paymybuddy.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ConnectionService connectionService;

    @MockBean
    private User userMock;

    @MockBean
    private Bank bankMock;

    String firstNameConst = "firstnametest";
    String lastNameConst = "lastnametest";
    String emailConst = "emailtest@test.us";
    String passwordConst = "password";
    String pseudoConst = "pseudotest";

    @Test
    void getAllUserControllerTest() throws Exception {

        // GIVEN
        List<User> users = new ArrayList<>();
        userMock = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        users.add(userMock);
        Mockito.when(userService.findAll()).thenReturn(users);

        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/user/all").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getOneUserControllerTest() throws Exception {

        // GIVEN
        userMock = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);

        Mockito.when(userService.findUser(any(User.class))).thenReturn(userMock);

        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/user").content(asJsonString(new User("", "", emailConst, "", "")))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$..firstname").value(firstNameConst))
                .andExpect(jsonPath("$..lastname").value(lastNameConst));
    }

    @Test
    void SaveUserControllerTest() throws Exception {

        // GIVEN
        userMock = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        Mockito.when(userService.create(any(User.class))).thenReturn(userMock);

        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/user/userInfo")
                        .content(asJsonString(
                                new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst)))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void ConnectUserControllerTest() throws Exception {

        // GIVEN
        userMock = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        Mockito.when(connectionService.connectUser(any(User.class))).thenReturn(true);

        // WHEN
        // THEN
        this.mockMvc
                .perform(get("/user/connect").content(asJsonString(new User("", "", emailConst, passwordConst, "")))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void CreateBankUserControllerTest() throws Exception {

        // GIVEN
        userMock = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        bankMock = new Bank();
        bankMock.setBicCode("biccodetest");
        bankMock.setNameBank("bankname");
        bankMock.setUser(userMock);
        Mockito.when(userService.createBank(any(Bank.class))).thenReturn(bankMock);

        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/user/createbank").content(asJsonString(bankMock))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void GetAllBankUserControllerTest() throws Exception {

        // GIVEN
        userMock = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        bankMock = new Bank();
        bankMock.setBicCode("biccodetest");
        bankMock.setNameBank("bankname");
        bankMock.setUser(userMock);
        List<Bank> banks = new ArrayList<>();
        banks.add(bankMock);
        Mockito.when(userService.findAllBankByUser(any(Bank.class))).thenReturn(banks);

        // WHEN
        // THEN
        this.mockMvc.perform(get("/user/bank").content(asJsonString(bankMock)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void CreateBuddyControllerTest() throws Exception {

        // GIVEN
        userMock = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);

        Mockito.when(userService.createBuddy(any(User.class))).thenReturn(userMock);

        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/user/createbuddy").content(asJsonString(userMock))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void ListBuddyControllerTest() throws Exception {

        // GIVEN
        userMock = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        List<User> buddys = new ArrayList<>();
        buddys.add(userMock);

        Mockito.when(userService.findAllBuddyByUser(any(User.class))).thenReturn(buddys);

        // WHEN
        // THEN
        this.mockMvc.perform(get("/user/buddy").content(asJsonString(userMock)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
