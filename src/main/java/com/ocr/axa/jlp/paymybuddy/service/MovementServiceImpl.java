package com.ocr.axa.jlp.paymybuddy.service;

import java.math.BigDecimal;
import java.util.Date;
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
import com.ocr.axa.jlp.paymybuddy.model.BankTransfer;
import com.ocr.axa.jlp.paymybuddy.model.Credit;
import com.ocr.axa.jlp.paymybuddy.model.Transfer;
import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;
import com.ocr.axa.jlp.paymybuddy.constant.*;

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

    /**
     * @param Credit
     * @return Credit created
     * 
     * A movement of credit is created and the balance of the account is updated
     * 
     * Control if user exist
     */
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

            credit.setSens("C");
            if (credit.getTypeCredit() != null) {
                if (credit.getTypeCredit().isEmpty()) {
                    credit.setTypeCredit("External");
                }
            }

            Credit creditCreated = movementDAO.save(credit);

            return creditCreated;

        } else {
            logger.error("add credit : KO, user not exist");
            throw new ControllerException("add credit : KO, user not exist");

        }
    }

    /**
     * @param BankTransfer
     * @return BankTransfer created
     * 
     * A movement of BankTransfer is created and the balance of the account is updated
     * 
     * Control : 
     *          if user exist 
     *          bank exist for this user
     *          balance is greater than the amount of the transfer
     */
    @Override
    public BankTransfer createBankTransfer(BankTransfer bankTransfer) {

        if (!bankDAO.existsById(bankTransfer.getBank().getId())) {
            logger.error("bank transfer : KO, bank not exist");
            throw new ControllerException("bank transfer : KO, bank not exist");
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
                throw new ControllerException("bank transfer : KO, amount too big");
            }
            newBalance = newBalance.subtract(bankTransfer.getAmount());
            accountMovement.setBalance(newBalance);

            bankTransfer.setAccount(accountMovement);
            bankTransfer.setSens("D");

            if (bankTransfer.getDateMovement() == null) {
                Date today = new Date();
                bankTransfer.setDateMovement(today);
            }
            ;

            BankTransfer bankTransferCreated = movementDAO.save(bankTransfer);

            return bankTransferCreated;

        } else {
            logger.error("bank transfer : KO, user not exist");
            throw new ControllerException("bank transfer : KO, user not exist");

        }

    }

    /**
     * @param Transfer
     * @return Transfer created
     * 
     * A movement of Transfer (debt) is created and the balance of the account is updated for the user
     * A movement of credit is created for the buddy
     * A movement of credit to the account of SYSTEM for the fees
     * 
     * Control : 
     *          user exist
     *          buddy exist for this user
     *          if the buddy is a buddy for this user
     *          balance is greater than the amount of the transfer plus the fees
     */
    @Override
    public Transfer createTransfer(Transfer transfer) {

        User userTransfer = userDAO.findByEmail(transfer.getAccount().getUser().getEmail());
        if (userTransfer != null) {

            // verify if buddy exist
            User buddyTransfer = userDAO.findByEmail(transfer.getUser().getEmail());
            if (buddyTransfer == null) {
                logger.error("transfer buddy : KO, buddy not exist");
                throw new ControllerException("transfer buddy : KO, buddy not exist");
            }
            ;
            transfer.setUser(buddyTransfer);

            // verify if buddy is a buddy for this user
            if (!userDAO.existsByEmailAndBuddies(transfer.getAccount().getUser().getEmail(), buddyTransfer)) {
                logger.error("transfer buddy : KO, it's not a buddy");
                throw new ControllerException("transfer buddy : KO, it's not a buddy");
            }

            // transfer
            Account accountMovement = accountDAO.findByUserId(userTransfer.getId());
            accountMovement.setUser(userTransfer);
            // balance
            BigDecimal newBalance = new BigDecimal(0);
            newBalance = newBalance.add(accountMovement.getBalance());

            // fees
            BigDecimal fees = transfer.getAmount().multiply(Fare.TAUXFEES);
            BigDecimal amountWithFees = transfer.getAmount().add(fees);

            // control balance
            if (amountWithFees.compareTo(accountMovement.getBalance()) > 0) {
                logger.error("transfer buddy : KO, amount too big");
                throw new ControllerException("transfer buddy : KO, amount too big");
            }
            newBalance = newBalance.subtract(amountWithFees);
            accountMovement.setBalance(newBalance);
            transfer.setAccount(accountMovement);
            transfer.setFees(fees);
            transfer.setSens("D");

            if (transfer.getDateMovement() == null) {
                Date today = new Date();
                transfer.setDateMovement(today);
            }
            ;

            Transfer transferCreated = movementDAO.save(transfer);

            // create movement for buddy
            Account accountBuddy = accountDAO.findByUserId(buddyTransfer.getId());
            Credit creditBuddy = new Credit();
            accountBuddy.setUser(buddyTransfer);
            creditBuddy.setComment("transfert " + userTransfer.getEmail());
            creditBuddy.setTransactionID(transferCreated.getId().toString());
            creditBuddy.setAmount(transfer.getAmount());
            creditBuddy.setTypeCredit("Internal");
            creditBuddy.setAccount(accountBuddy);
            createCredit(creditBuddy);

            // create movement for PayeMyBuddy
            User userSystem = userDAO.findByEmail(Fare.EMAILPAYEMYBUDDY);
            Account accountSystem = accountDAO.findByUserId(userSystem.getId());
            accountSystem.setUser(userSystem);
            Credit creditSystem = new Credit();
            creditSystem.setComment("transfert " + userTransfer.getEmail());
            creditSystem.setTransactionID(transferCreated.getId().toString());
            creditSystem.setAmount(fees);
            creditSystem.setTypeCredit("Internal");
            creditSystem.setAccount(accountSystem);
            createCredit(creditSystem);

            return transferCreated;

        } else {
            logger.error("buddy transfer : KO, user not exist");
            throw new ControllerException("buddy transfer : KO, user not exist");

        }

    }

}
