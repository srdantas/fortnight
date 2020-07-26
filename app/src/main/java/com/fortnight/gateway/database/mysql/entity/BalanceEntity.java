package com.fortnight.gateway.database.mysql.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Data
@Entity(name = "balances")
public class BalanceEntity {

    @Id
    private String document;
    private BigDecimal amount;

    @MapsId
    @OneToOne
    @JoinColumn(name = "document")
    private AccountEntity account;

}
