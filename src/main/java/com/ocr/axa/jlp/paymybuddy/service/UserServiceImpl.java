package com.ocr.axa.jlp.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocr.axa.jlp.paymybuddy.DAO.UserDAO;
import com.ocr.axa.jlp.paymybuddy.model.User;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserDAO userDAO;

    @Override
    public User save(User user) {
       
        return userDAO.create(user);
    }

}
