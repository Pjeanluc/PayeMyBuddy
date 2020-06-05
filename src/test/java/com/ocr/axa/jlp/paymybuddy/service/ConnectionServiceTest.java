package com.ocr.axa.jlp.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ocr.axa.jlp.paymybuddy.DAO.UserDAO;
import com.ocr.axa.jlp.paymybuddy.model.User;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
class ConnectionServiceTest {
    
    @MockBean
    UserDAO userDAOMock;
    
    @Autowired
    ConnectionService connectionServiceTest;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    String firstNameConst = "firstnametest";
    String lastNameConst = "lastnametest";
    String emailConst = "emailtest@test.us";
    String passwordConst = "password";
    String pseudoConst = "pseudotest";

    @Test
    void connectUserTest() {
     // GIVEN
        User userToConnectTest = new User();
        userToConnectTest.setEmail("email@test.fr");
        userToConnectTest.setPassword("password");
        User userTest = new User(firstNameConst, lastNameConst, emailConst, "", pseudoConst);
        userTest.setPassword(passwordEncoder.encode("password"));
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userTest);

        // WHEN
        // THEN
        assertThat(connectionServiceTest.connectUser(userToConnectTest)).isEqualTo(true);
    }
    
    @Test
    void connectUserFailedTest() {
     // GIVEN
        User userToConnectTest = new User();
        userToConnectTest.setEmail("email@test.fr");
        userToConnectTest.setPassword("notthepassword");
        User userTest = new User(firstNameConst, lastNameConst, emailConst, "", pseudoConst);
        userTest.setPassword(passwordEncoder.encode("password"));
        Mockito.when(userDAOMock.findByEmail(any(String.class))).thenReturn(userTest);

        // WHEN
        // THEN
        assertThat(connectionServiceTest.connectUser(userToConnectTest)).isEqualTo(false);
    }

}
