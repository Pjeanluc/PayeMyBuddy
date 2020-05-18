package com.ocr.axa.jlp.paymybuddy.DAO;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.ocr.axa.jlp.paymybuddy.model.User;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class UserDAOTest {
    @Autowired
    UserDAO userDAO;

    @Test
    void saveOneUser() {

        // GIVEN
        User userTest = new User();
        userTest.setFirstname("Firstnametest");
        userTest.setLastname("Lastnametest");
        userTest.setEmail("email@test.fr");
        userTest.setPassword("password");
        userTest.setPseudo("pseudotest");

        // WHEN
        User userAdded = userDAO.save(userTest);

        // THEN
        assertThat(userAdded.getEmail()).isEqualTo(userTest.getEmail());

    }

}
