package com.ocr.axa.jlp.paymybuddy.web.controller;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ocr.axa.jlp.paymybuddy.model.BankTransfer;
import com.ocr.axa.jlp.paymybuddy.model.Credit;
import com.ocr.axa.jlp.paymybuddy.service.MovementService;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;

@RestController
@RequestMapping(path = "/movement")
public class MovementController {
    private static final Logger logger = LogManager.getLogger("generalController");

    @Autowired
    MovementService movementService;

    @PostMapping("/credit")
    public ResponseEntity<Credit> creditAccount(@RequestBody Credit credit) {

        if (credit.getAccount().getUser().getEmail().isEmpty()) {
            logger.error("credit account : KO, Account email is required");
            throw new ControllerException("Account email is required");
        }

        if (credit.getTransactionID().isEmpty()) {
            logger.error("credit account : KO, transaction Id is required");
            throw new ControllerException("transaction Id is required");
        }

        Credit creditAdded = movementService.createCredit(credit);
        if (creditAdded == null) {
            logger.error("add credit : KO");
            throw new ControllerException("add credit : KO");

        } else {
            logger.info("add credit OK ");
            return new ResponseEntity(creditAdded, HttpStatus.OK);
        }
    }

    @PostMapping("/banktransfer")
    public ResponseEntity<BankTransfer> bankTransfert(@RequestBody BankTransfer bankTransfer) {

        if (bankTransfer.getAccount().getUser().getEmail().isEmpty()) {
            logger.error("bank transfer : KO, Account email is required");
            throw new ControllerException("Account email is required");
        }

        if (bankTransfer.getBank().getId() <= 0) {
            logger.error("bank transfer : KO, bank id is required");
            throw new ControllerException("bank id is required");
        }
        if (bankTransfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("bank transfer : KO, amount nul or negative");
            throw new ControllerException("amount nul or negative");
        }

        BankTransfer bankTransferAdded = movementService.createBankTransfer(bankTransfer);
        if (bankTransferAdded == null) {
            logger.error("bank transfer : KO");
            throw new ControllerException("bank transfer : KO");

        } else {
            logger.info("bank transfer OK ");
            return new ResponseEntity(bankTransferAdded, HttpStatus.OK);
        }
    }

}
