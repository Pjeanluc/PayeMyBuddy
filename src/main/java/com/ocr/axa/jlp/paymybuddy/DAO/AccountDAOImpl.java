package com.ocr.axa.jlp.paymybuddy.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ocr.axa.jlp.paymybuddy.model.Account;
import com.ocr.axa.jlp.paymybuddy.repository.AccountRepository;

@Component
public class AccountDAOImpl implements AccountDAO {
    private static final Logger logger = LogManager.getLogger("generalController");

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Long save(Account account) {

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            logger.error("creation account KO");
            return 0L;
        }
        logger.info("creation account OK");
        return account.getId();
    }

}
