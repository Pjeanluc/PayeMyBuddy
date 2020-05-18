package com.ocr.axa.jlp.paymybuddy.DAO;

import java.util.List;

import com.ocr.axa.jlp.paymybuddy.model.User;

public interface UserDAO {
    
    public User save(User user);

    public List<User> findAll();

}
