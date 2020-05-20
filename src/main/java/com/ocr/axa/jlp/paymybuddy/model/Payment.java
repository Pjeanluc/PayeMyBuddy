package com.ocr.axa.jlp.paymybuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payment")
@PrimaryKeyJoinColumn(name = "id")
public class Payment extends Movement {

    @Column(length = 100)
    @NotNull
    private String transactionID;

}
