package com.ocr.axa.jlp.paymybuddy.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ocr.axa.jlp.paymybuddy.DAO.AccountDAO;
import com.ocr.axa.jlp.paymybuddy.DAO.UserDAO;
import com.ocr.axa.jlp.paymybuddy.model.Account;
import com.ocr.axa.jlp.paymybuddy.model.User;

@Service
@Transactional
public class CreateServiceImpl implements CreateService {
    private static final Logger logger = LogManager.getLogger("generalController");

    @Autowired
    UserDAO userDAO;
    
    @Autowired
    AccountDAO accountDAO;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Long create(User user) {

        Account account = new Account();
        account.setCurrency(954);
        BigDecimal b = new BigDecimal(0.00);
        account.setBalance(b);
        account.setUser(user);        
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (accountDAO.save(account) > 0) {
            return account.getUser().getId();
        } else
            return null;
    }

    @Override
    public List<User> findAll() {

        return userDAO.findAll();
    }

}
