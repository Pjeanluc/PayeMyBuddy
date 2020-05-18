package com.ocr.axa.jlp.paymybuddy.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.repository.UserRepository;

@Component
public class USerDAOImpl implements UserDAO {
    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {

        userRepository.save(user);

        User userAdded = userRepository.findUserByEmail(user.getEmail());
        return userAdded;
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

}
