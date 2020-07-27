package com.fortnight.gateways.database.mysql.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "balances")
public class BalanceEntity {

    @Id
    private String document;
    private BigDecimal amount;

}
