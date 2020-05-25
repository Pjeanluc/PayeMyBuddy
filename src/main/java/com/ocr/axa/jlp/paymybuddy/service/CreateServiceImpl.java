package com.ocr.axa.jlp.paymybuddy.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public User create(User user) {

        Account account = new Account();
        account.setCurrency(954);
        BigDecimal b = new BigDecimal(0.00);
        account.setBalance(b);
        account.setUser(user);
        
        User userAdded = userDAO.save(user);

        if (accountDAO.save(account)) {
            return userAdded;
        } else
            return null;
    }

    @Override
    public List<User> findAll() {

        return userDAO.findAll();
    }

}
