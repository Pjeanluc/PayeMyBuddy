package com.ocr.axa.jlp.paymybuddy.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Banktransfer")
@PrimaryKeyJoinColumn(name = "id")
public class BankTransfer extends Movement {
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bank_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_banktransfert_bank"))
    private Bank bank;

}
