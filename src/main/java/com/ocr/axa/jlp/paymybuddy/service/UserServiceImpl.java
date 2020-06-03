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
import com.ocr.axa.jlp.paymybuddy.DAO.BankDAO;
import com.ocr.axa.jlp.paymybuddy.DAO.UserDAO;
import com.ocr.axa.jlp.paymybuddy.model.Account;
import com.ocr.axa.jlp.paymybuddy.model.Bank;
import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger("generalController");

    @Autowired
    UserDAO userDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    BankDAO bankDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
       

        if (!userDAO.existsByEmail(user.getEmail())) {
            User userCreated = new User();;
            Account account = new Account();
            account.setCurrency(954);
            BigDecimal b = new BigDecimal(0.00);
            account.setBalance(b);
            account.setUser(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            accountDAO.save(account);
            userCreated = account.getUser();
            
            return userCreated;
        } else {
            logger.error("Email already exist");
            return null;
        }
    }

    @Override
    public List<User> findAll() {

        return userDAO.findAll();
    }

    @Override
    public Bank createBank(Bank bank) {

        User userBank = userDAO.findByEmail(bank.getUser().getEmail());
        if (userBank != null) {
            bank.setUser(userBank);
            bankDAO.save(bank);
        } else {
            logger.error("create bank : KO, user not exist");
            throw new ControllerException("create bank : KO, user not exist");
        }
        logger.error("create bank : OK");
        return bank;
    }

    @Override
    public List<Bank> findAllBankByUser(Bank bank) {
        User userBank = userDAO.findByEmail(bank.getUser().getEmail());

        return bankDAO.findByUserId(userBank.getId());
    }

    @Override
    public User createBuddy(User user) {

        User userToUpdate = userDAO.findByEmail(user.getEmail());

        if (userToUpdate != null) {
            userToUpdate.setBuddies(user.getBuddies());
        } else {
            logger.error("create buddy : KO, user not exist");
            throw new ControllerException("create buddy : KO, user not exist");
        }

        return userToUpdate;
    }

    @Override
    public List<User> findAllBuddyByUser(User user) {
        User userToFind = userDAO.findByEmail(user.getEmail());

        return userToFind.getBuddies();
    }

    @Override
    public User findUser(User user) {
        return userDAO.findByEmail(user.getEmail());
    }

}
