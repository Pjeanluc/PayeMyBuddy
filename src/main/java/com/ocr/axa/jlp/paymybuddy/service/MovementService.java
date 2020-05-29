package com.ocr.axa.jlp.paymybuddy.service;

import com.ocr.axa.jlp.paymybuddy.model.BankTransfer;
import com.ocr.axa.jlp.paymybuddy.model.Credit;
import com.ocr.axa.jlp.paymybuddy.model.Transfer;

public interface MovementService {

    Credit createCredit(Credit credit);

    BankTransfer createBankTransfer(BankTransfer bankTransfer);

    Transfer createTransfer(Transfer transfer);

}
