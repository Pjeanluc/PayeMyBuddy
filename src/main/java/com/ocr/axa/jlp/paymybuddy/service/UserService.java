package com.ocr.axa.jlp.paymybuddy.service;

import java.util.List;
import com.ocr.axa.jlp.paymybuddy.model.Bank;
import com.ocr.axa.jlp.paymybuddy.model.User;

public interface UserService {
    
    public User create(User user);

    public List<User> findAll();

    public Bank createBank(Bank bank);

    public List<Bank> findAllBankByUser(Bank bank);

    public List<User> findAllBuddyByUser(User user);

    public User createBuddy(User user);

    public User findUser(User user);

}
