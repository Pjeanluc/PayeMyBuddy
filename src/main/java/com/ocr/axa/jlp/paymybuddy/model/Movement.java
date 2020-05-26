package com.ocr.axa.jlp.paymybuddy.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Movement")
@Inheritance(strategy = InheritanceType.JOINED)
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateMovement;

    @Column(columnDefinition = "Decimal(8,2)", nullable = false)
    private BigDecimal amount;
    
    @Column(length = 100)
    @NotNull
    private String Comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_movement_account"))
    private Account account;

}
