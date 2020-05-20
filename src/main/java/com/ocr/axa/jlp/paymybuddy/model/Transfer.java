package com.ocr.axa.jlp.paymybuddy.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "transfer")
@PrimaryKeyJoinColumn(name = "id")
public class Transfer extends Movement {
    
    @Column(columnDefinition = "Decimal(8,2)", nullable = false)
    private BigDecimal fees;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="buddy_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_transfer_user"))
    private User user;

}
