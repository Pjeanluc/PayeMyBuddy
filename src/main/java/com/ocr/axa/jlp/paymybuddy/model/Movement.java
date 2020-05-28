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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateMovement() {
        Date localDate = dateMovement;
        return localDate;
    }

    public void setDateMovement(Date dateMovement) {
        if (dateMovement == null) {
            this.dateMovement = null;
        } else {
            this.dateMovement = new Date(dateMovement.getTime());
        }
        this.dateMovement = dateMovement;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
