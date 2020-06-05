package com.ocr.axa.jlp.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ocr.axa.jlp.paymybuddy.DAO.AccountDAO;
import com.ocr.axa.jlp.paymybuddy.DAO.BankDAO;
import com.ocr.axa.jlp.paymybuddy.DAO.UserDAO;
import com.ocr.axa.jlp.paymybuddy.model.Account;
import com.ocr.axa.jlp.paymybuddy.model.Bank;
import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
class UserServiceTest {

    @MockBean
    UserDAO userDAOMock;

    @MockBean
    AccountDAO accountDAOMock;

    @MockBean
    BankDAO bankDAOMock;

    @Autowired
    UserService userServiceTest;

    String firstNameConst = "firstnametest";
    String lastNameConst = "lastnametest";
    String emailConst = "emailtest@test.us";
    String passwordConst = "password";
    String pseudoConst = "pseudotest";

    @Test
    void createUserTest() {
        // GIVEN
        User userTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        Account accountTest = new Account();
        accountTest.setUser(userTest);

        Mockito.when(accountDAOMock.save(any(Account.class))).thenReturn(accountTest);

        // WHEN
        User userCreatedTest = userServiceTest.create(userTest);

        // THEN
        assertThat(userCreatedTest.getFirstname()).isEqualTo(firstNameConst);
        assertThat(userCreatedTest.getLastname()).isEqualTo(lastNameConst);

    }

    @Test
    void createUserWithExistingEmailTest() {
        // GIVEN
        User userTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);

        Mockito.when(userDAOMock.existsByEmail(any(String.class))).thenReturn(true);

        // WHEN
        // THEN
        assertThat(userServiceTest.create(userTest)).isNull();

    }

    @Test
    void createBankUserTest() {
        // GIVEN
        User userTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        Bank bankTest = new Bank();
        bankTest.setUser(userTest);
        bankTest.setBicCode("biccodetest");
        bankTest.setNameBank("banquenametest");

        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userTest);
        Mockito.when(bankDAOMock.save(any(Bank.class))).thenReturn(bankTest);

        // WHEN
        Bank bankCreatedTest = userServiceTest.createBank(bankTest);

        // THEN
        assertThat(bankCreatedTest.getUser().getFirstname()).isEqualTo(firstNameConst);
        assertThat(bankCreatedTest.getBicCode()).isEqualTo("biccodetest");

    }

    @Test
    void createBankWithNotExistingUserTest() throws Exception {
        // GIVEN
        User userTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        Bank bankTest = new Bank();
        bankTest.setUser(userTest);
        bankTest.setBicCode("biccodetest");
        bankTest.setNameBank("banquenametest");

        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(null);

        // WHEN
        // THEN
        try {
            Bank bankCreatedTest = userServiceTest.createBank(bankTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : create bank : KO, user not exist");
        }

    }
    @Test
    void createBuddyUserTest() {
        // GIVEN
        User userTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        List<User> buddys = new ArrayList<>();
        User buddyTest = new User();
        buddys.add(buddyTest);
        userTest.setBuddies(buddys);

        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userTest);
        
        // WHEN        
        User userUpdated = userServiceTest.createBuddy(userTest);
        
        // THEN
        assertThat(userUpdated.getBuddies().size()).isEqualTo(1);

    }
    
    @Test
    void createBuddyWithNotExistingUserTest() {
        // GIVEN
        User userTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        List<User> buddys = new ArrayList<>();
        User buddyTest = new User();
        buddys.add(buddyTest);

        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(null);
        
        // WHEN
        // THEN
        try {
            User userUpdated = userServiceTest.createBuddy(userTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : create buddy : KO, user not exist");
        }
    }

}
