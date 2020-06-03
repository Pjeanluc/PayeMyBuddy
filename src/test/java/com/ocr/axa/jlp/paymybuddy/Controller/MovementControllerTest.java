package com.ocr.axa.jlp.paymybuddy.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ocr.axa.jlp.paymybuddy.model.Account;
import com.ocr.axa.jlp.paymybuddy.model.Bank;
import com.ocr.axa.jlp.paymybuddy.model.BankTransfer;
import com.ocr.axa.jlp.paymybuddy.model.Credit;
import com.ocr.axa.jlp.paymybuddy.model.Transfer;
import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.service.MovementService;
import com.ocr.axa.jlp.paymybuddy.service.UserService;
import static com.ocr.axa.jlp.paymybuddy.Controller.UserControllerTest.asJsonString;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
class MovementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovementService movementService;

    @MockBean
    private Credit creditMock;
    
    @MockBean
    private BankTransfer bankTransferMock;
    
    @MockBean
    private Transfer transferMock;
    
    @MockBean
    private User userMock;
    
    @MockBean
    private Bank bankMock;
    
    @MockitoSettings
    private Account accountMock;

    @Test
    void createMovementCreditTest() throws Exception {

        // GIVEN

        creditMock = new Credit();
        creditMock.setTransactionID("transactionidtest");
        userMock = new User();
        userMock.setEmail("email@test.fr");
        accountMock = new Account();
        accountMock.setUser(userMock);
        creditMock.setAccount(accountMock);
        
        Mockito.when(movementService.createCredit(any(Credit.class))).thenReturn(creditMock);

        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/movement/credit").content(asJsonString(creditMock))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
        
        
    }
    
    @Test
    void createMovementBankTransferTest() throws Exception {

        // GIVEN
        userMock = new User();
        userMock.setEmail("email@test.fr");
        accountMock = new Account();
        accountMock.setUser(userMock);
        bankMock = new Bank();
        bankMock.setId(1L);
        bankTransferMock = new BankTransfer();
        bankTransferMock.setAccount(accountMock);
        bankTransferMock.setBank(bankMock);
        BigDecimal b = new BigDecimal("100.00");
        bankTransferMock.setAmount(b);
        
        Mockito.when(movementService.createBankTransfer(any(BankTransfer.class))).thenReturn(bankTransferMock);

        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/movement/banktransfer").content(asJsonString(bankTransferMock))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
        
        
    }
    
    @Test
    void createPaymentTest() throws Exception {

        // GIVEN
        userMock = new User();
        userMock.setEmail("email@test.fr");
        accountMock = new Account();
        accountMock.setUser(userMock);
   
        transferMock = new Transfer();
        transferMock.setAccount(accountMock);
        transferMock.setUser(userMock);
        BigDecimal b = new BigDecimal("100.00");
        transferMock.setAmount(b);
        
        Mockito.when(movementService.createTransfer(any(Transfer.class))).thenReturn(transferMock);

        // WHEN
        // THEN
        this.mockMvc
                .perform(post("/movement/payment").content(asJsonString(transferMock))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
        
        
    }

}
