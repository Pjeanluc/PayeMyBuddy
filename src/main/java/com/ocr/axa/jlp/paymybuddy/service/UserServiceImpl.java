package com.ocr.axa.jlp.paymybuddy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocr.axa.jlp.paymybuddy.DAO.UserDAO;
import com.ocr.axa.jlp.paymybuddy.model.User;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserDAO userDAO;

    @Override
    public User create(User user) {
       
        return userDAO.save(user);
    }

    @Override
    public List<User> findAll() {
        
        return userDAO.findAll();
    }

}
