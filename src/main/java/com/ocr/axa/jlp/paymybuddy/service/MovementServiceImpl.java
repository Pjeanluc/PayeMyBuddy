package com.ocr.axa.jlp.paymybuddy.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocr.axa.jlp.paymybuddy.DAO.AccountDAO;
import com.ocr.axa.jlp.paymybuddy.DAO.BankDAO;
import com.ocr.axa.jlp.paymybuddy.DAO.MovementDAO;
import com.ocr.axa.jlp.paymybuddy.DAO.UserDAO;
import com.ocr.axa.jlp.paymybuddy.model.Account;
import com.ocr.axa.jlp.paymybuddy.model.Bank;
import com.ocr.axa.jlp.paymybuddy.model.BankTransfer;
import com.ocr.axa.jlp.paymybuddy.model.Credit;
import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;

@Service
@Transactional
public class MovementServiceImpl implements MovementService {
    private static final Logger logger = LogManager.getLogger("generalController");

    @Autowired
    MovementDAO movementDAO;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    BankDAO bankDAO;

    @Override
    public Credit createCredit(Credit credit) {

        User userMovement = userDAO.findByEmail(credit.getAccount().getUser().getEmail());
        if (userMovement != null) {
            Account accountMovement = accountDAO.findByUserId(userMovement.getId());
            accountMovement.setUser(userMovement);
            // update balance
            BigDecimal newBalance = new BigDecimal(0);
            newBalance = newBalance.add(accountMovement.getBalance());
            newBalance = newBalance.add(credit.getAmount());
            accountMovement.setBalance(newBalance);

            credit.setAccount(accountMovement);

            if (credit.getDateMovement() == null) {
                Date today = new Date();
                credit.setDateMovement(today);
            }
            ;

            Credit creditCreated = movementDAO.save(credit);

            return creditCreated;

        } else {
            logger.error("add credit : KO, user not exist");
            throw new ControllerException("add credit : KO, user not exist");

        }
    }

    @Override
    public BankTransfer createBankTransfer(BankTransfer bankTransfer) {

        if (!bankDAO.existsById(bankTransfer.getBank().getId())) {
            logger.error("bank transfer : KO, bank not exist");
            throw new ControllerException("bank transfert : KO, bank not exist");
        }

        User userMovement = userDAO.findByEmail(bankTransfer.getAccount().getUser().getEmail());
        if (userMovement != null) {
            Account accountMovement = accountDAO.findByUserId(userMovement.getId());
            accountMovement.setUser(userMovement);
            // update balance
            BigDecimal newBalance = new BigDecimal(0);

            newBalance = newBalance.add(accountMovement.getBalance());
            if (newBalance.compareTo(bankTransfer.getAmount()) < 0) {
                logger.error("bank transfer : KO, amount too big");
                throw new ControllerException("bank transfert : KO, amount too big");
            }
            newBalance = newBalance.subtract(bankTransfer.getAmount());
            accountMovement.setBalance(newBalance);

            bankTransfer.setAccount(accountMovement);

            if (bankTransfer.getDateMovement() == null) {
                Date today = new Date();
                bankTransfer.setDateMovement(today);
            }
            ;

            BankTransfer bankTransferCreated = movementDAO.save(bankTransfer);

            return bankTransferCreated;

        } else {
            logger.error("bank transfer : KO, user not exist");
            throw new ControllerException("bank transfert : KO, user not exist");

        }

    }

}
