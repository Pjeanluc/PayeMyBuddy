package com.ocr.axa.jlp.paymybuddy.service;

import java.util.List;

import com.ocr.axa.jlp.paymybuddy.model.User;

public interface CreateService {
    
    public Long create(User user);

    public List<User> findAll();

}
