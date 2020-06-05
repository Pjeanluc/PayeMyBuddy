package com.ocr.axa.jlp.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;

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
import com.ocr.axa.jlp.paymybuddy.DAO.MovementDAO;
import com.ocr.axa.jlp.paymybuddy.DAO.UserDAO;
import com.ocr.axa.jlp.paymybuddy.model.Account;
import com.ocr.axa.jlp.paymybuddy.model.Bank;
import com.ocr.axa.jlp.paymybuddy.model.BankTransfer;
import com.ocr.axa.jlp.paymybuddy.model.Credit;
import com.ocr.axa.jlp.paymybuddy.model.Movement;
import com.ocr.axa.jlp.paymybuddy.model.Transfer;
import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
class MovementServiceTest {

    @Autowired
    MovementService movementServiceTest;

    @MockBean
    MovementDAO movementDAOMock;

    @MockBean
    AccountDAO accountDAOMock;

    @MockBean
    UserDAO userDAOMock;

    @MockBean
    BankDAO bankDAOMock;

    String firstNameConst = "firstnametest";
    String lastNameConst = "lastnametest";
    String emailConst = "emailtest@test.us";
    String passwordConst = "password";
    String pseudoConst = "pseudotest";

    @Test
    void createCreditTest() {
        // GIVEN
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        Account accountMovementTest = new Account();
        accountMovementTest.setUser(userMovementTest);
        BigDecimal b = new BigDecimal(100);
        accountMovementTest.setBalance(b);
        accountMovementTest.setCurrency(954);
        accountMovementTest.setId(1L);
        Mockito.when(accountDAOMock.findByUserId(any(Long.class))).thenReturn(accountMovementTest);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userMovementTest);

        Credit creditTest = new Credit();
        User userTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        creditTest.setTransactionID("transactionidtest");
        creditTest.setAccount(accountTest);
        creditTest.setAmount(b);
        creditTest.setComment("commenttest");
        creditTest.setTypeCredit("");

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(creditTest);

        // WHEN
        Credit movementCreditCreatedTest = movementServiceTest.createCredit(creditTest);

        // THEN
        assertThat(movementCreditCreatedTest.getSens()).isEqualTo("C");
        assertThat(movementCreditCreatedTest.getTypeCredit()).isEqualTo("External");
    }

    @Test
    void createBankTransferTest() {
        // GIVEN
        // mock DAO
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userMovementTest);

        Account accountMovementTest = new Account();
        accountMovementTest.setUser(userMovementTest);
        BigDecimal b = new BigDecimal(100);
        accountMovementTest.setBalance(b);
        accountMovementTest.setCurrency(954);
        accountMovementTest.setId(1L);
        Mockito.when(accountDAOMock.findByUserId(any(Long.class))).thenReturn(accountMovementTest);

        Mockito.when(bankDAOMock.existsById(any(Long.class))).thenReturn(true);

        // movement to test
        BankTransfer bankTransferTest = new BankTransfer();
        User userTest = new User();
        userTest.setEmail(emailConst);
        Bank bankTest = new Bank();
        bankTest.setId(1L);
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        BigDecimal amountMovement = new BigDecimal(10);
        bankTransferTest.setAmount(amountMovement);
        bankTransferTest.setAccount(accountTest);
        bankTransferTest.setBank(bankTest);

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(bankTransferTest);

        // WHEN
        BankTransfer movementBankTranfertCreatedTest = movementServiceTest.createBankTransfer(bankTransferTest);

        // THEN
        assertThat(movementBankTranfertCreatedTest.getSens()).isEqualTo("D");
        assertThat(movementBankTranfertCreatedTest.getDateMovement()).isNotNull();
    }

    /**
     * Test : creation of movement bank transfer with a code bank not existing
     */
    @Test
    void createBankTransferWithBankNotExistingTest() {
        // GIVEN
        // mock DAO
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userMovementTest);

        Account accountMovementTest = new Account();
        accountMovementTest.setUser(userMovementTest);
        BigDecimal b = new BigDecimal(100);
        accountMovementTest.setBalance(b);
        accountMovementTest.setCurrency(954);
        accountMovementTest.setId(1L);
        Mockito.when(accountDAOMock.findByUserId(any(Long.class))).thenReturn(accountMovementTest);

        Mockito.when(bankDAOMock.existsById(any(Long.class))).thenReturn(false);

        // movement to test
        BankTransfer bankTransferTest = new BankTransfer();
        User userTest = new User();
        userTest.setEmail(emailConst);
        Bank bankTest = new Bank();
        bankTest.setId(1L);
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        BigDecimal amountMovement = new BigDecimal(10);
        bankTransferTest.setAmount(amountMovement);
        bankTransferTest.setAccount(accountTest);
        bankTransferTest.setBank(bankTest);

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(bankTransferTest);

        // WHEN
        // THEN
        try {
            BankTransfer movementBankTranfertCreatedTest = movementServiceTest.createBankTransfer(bankTransferTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : bank transfer : KO, bank not exist");
        }
    }

    /**
     * Test : creation of movement bank transfer with a user not existing
     */
    @Test
    void createBankTransferWithUserNotExistingTest() {
        // GIVEN
        // mock DAO
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(null);

        Account accountMovementTest = new Account();
        accountMovementTest.setUser(userMovementTest);
        BigDecimal b = new BigDecimal(100);
        accountMovementTest.setBalance(b);
        accountMovementTest.setCurrency(954);
        accountMovementTest.setId(1L);
        Mockito.when(accountDAOMock.findByUserId(any(Long.class))).thenReturn(accountMovementTest);

        Mockito.when(bankDAOMock.existsById(any(Long.class))).thenReturn(true);

        // movement to test
        BankTransfer bankTransferTest = new BankTransfer();
        User userTest = new User();
        userTest.setEmail(emailConst);
        Bank bankTest = new Bank();
        bankTest.setId(1L);
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        BigDecimal amountMovement = new BigDecimal(10);
        bankTransferTest.setAmount(amountMovement);
        bankTransferTest.setAccount(accountTest);
        bankTransferTest.setBank(bankTest);

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(bankTransferTest);

        // WHEN
        // THEN
        try {
            BankTransfer movementBankTranfertCreatedTest = movementServiceTest.createBankTransfer(bankTransferTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : bank transfer : KO, user not exist");
        }
    }

    /**
     * Test : creation of movement bank transfer with a amount greater than the
     * balance of the account
     */
    @Test
    void createBankTransferWithAmmountTooBigTest() {
        // GIVEN
        // mock DAO
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userMovementTest);

        Account accountMovementTest = new Account();
        accountMovementTest.setUser(userMovementTest);
        BigDecimal b = new BigDecimal(100);
        accountMovementTest.setBalance(b);
        accountMovementTest.setCurrency(954);
        accountMovementTest.setId(1L);
        Mockito.when(accountDAOMock.findByUserId(any(Long.class))).thenReturn(accountMovementTest);

        Mockito.when(bankDAOMock.existsById(any(Long.class))).thenReturn(true);

        // movement to test
        BankTransfer bankTransferTest = new BankTransfer();
        User userTest = new User();
        userTest.setEmail(emailConst);
        Bank bankTest = new Bank();
        bankTest.setId(1L);
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        BigDecimal amountMovement = new BigDecimal(101);
        bankTransferTest.setAmount(amountMovement);
        bankTransferTest.setAccount(accountTest);
        bankTransferTest.setBank(bankTest);

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(bankTransferTest);

        // WHEN
        // THEN
        try {
            BankTransfer movementBankTranfertCreatedTest = movementServiceTest.createBankTransfer(bankTransferTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : bank transfer : KO, amount too big");
        }
    }

    /**
     * Test : creation of movement transfer with a user not existing
     */
    @Test
    void createTransferWithUserNotExistTest() {
        // GIVEN
        // mock DAO
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(null);

        // movement to test
        Transfer transferTest = new Transfer();
        User userTest = new User();
        userTest.setEmail(emailConst);
        User buddyTest = new User();
        buddyTest.setEmail("buddy@test.fr");
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        BigDecimal amountMovement = new BigDecimal(101);
        transferTest.setAmount(amountMovement);
        transferTest.setAccount(accountTest);
        transferTest.setUser(buddyTest);

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(transferTest);

        // WHEN
        // THEN
        try {
            Transfer tranfertCreatedTest = movementServiceTest.createTransfer(transferTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : buddy transfer : KO, user not exist");
        }
    }

    /**
     * Test : creation of movement transfer with a buddy not existing
     */
    @Test
    void createTransferWithBuddyNotExistTest() {
        // GIVEN
        // mock DAO
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userMovementTest).thenReturn(null);

        // movement to test
        Transfer transferTest = new Transfer();
        User userTest = new User();
        userTest.setEmail(emailConst);
        User buddyTest = new User();
        buddyTest.setEmail("buddy@test.fr");
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        BigDecimal amountMovement = new BigDecimal(101);
        transferTest.setAmount(amountMovement);
        transferTest.setAccount(accountTest);
        transferTest.setUser(buddyTest);

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(transferTest);

        // WHEN
        // THEN
        try {
            Transfer tranfertCreatedTest = movementServiceTest.createTransfer(transferTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : transfer buddy : KO, buddy not exist");
        }

    }

    /**
     * Test : creation of movement transfer with a buddy not à buddy for this user
     */
    @Test
    void createTransferWithBuddyNotBuddyTest() {
        // GIVEN
        // mock DAO
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        User buddyMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        buddyMovementTest.setId(1L);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userMovementTest)
                .thenReturn(buddyMovementTest);
        Mockito.when(userDAOMock.existsByEmailAndBuddies(any(String.class), any(User.class))).thenReturn(false);

        // movement to test
        Transfer transferTest = new Transfer();
        User userTest = new User();
        userTest.setEmail(emailConst);
        User buddyTest = new User();
        buddyTest.setEmail("buddy@test.fr");
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        BigDecimal amountMovement = new BigDecimal(101);
        transferTest.setAmount(amountMovement);
        transferTest.setAccount(accountTest);
        transferTest.setUser(buddyTest);

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(transferTest);

        // WHEN
        // THEN
        try {
            Transfer tranfertCreatedTest = movementServiceTest.createTransfer(transferTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : transfer buddy : KO, it's not a buddy");
        }
    }

    /**
     * Test : creation of movement transfer with a amount too big (> balance of the
     * account)
     */
    @Test
    void createTransferWithAmountTooBigTest() {
        // GIVEN
        // mock DAO
        User userMovementTest = new User(firstNameConst, lastNameConst, emailConst, passwordConst, pseudoConst);
        userMovementTest.setId(1L);
        User buddyMovementTest = new User(firstNameConst, lastNameConst, "buddy@test.fr", passwordConst, pseudoConst);
        buddyMovementTest.setId(1L);
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userMovementTest)
                .thenReturn(buddyMovementTest);
        Mockito.when(userDAOMock.existsByEmailAndBuddies(any(String.class), any(User.class))).thenReturn(true);

        Account accountMovementTest = new Account();
        accountMovementTest.setUser(userMovementTest);
        BigDecimal b = new BigDecimal(100);
        accountMovementTest.setBalance(b);
        accountMovementTest.setCurrency(954);
        accountMovementTest.setId(1L);
        Mockito.when(accountDAOMock.findByUserId(any(Long.class))).thenReturn(accountMovementTest);

        // movement to test
        Transfer transferTest = new Transfer();
        User userTest = new User();
        userTest.setEmail(emailConst);
        User buddyTest = new User();
        buddyTest.setEmail("buddy@test.fr");
        Account accountTest = new Account();
        accountTest.setUser(userTest);
        BigDecimal amountMovement = new BigDecimal(100);
        transferTest.setAmount(amountMovement);
        transferTest.setAccount(accountTest);
        transferTest.setUser(buddyTest);

        Mockito.when(movementDAOMock.save(any(Movement.class))).thenReturn(transferTest);

        // WHEN
        // THEN
        try {
            Transfer tranfertCreatedTest = movementServiceTest.createTransfer(transferTest);
        } catch (ControllerException e) {
            assertThat(e).hasMessage("ERROR : transfer buddy : KO, amount too big");
        }

    }
}
