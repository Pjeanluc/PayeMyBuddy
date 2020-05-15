package com.ocr.axa.jlp.paymybuddy.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.repository.UserRepository;

@Component
public class USerDAOImpl implements UserDAO {
    @Autowired
    UserRepository userRepository;

    @Override
    public User create(User user) {

       userRepository.save(user);
        return null;
    }

}
