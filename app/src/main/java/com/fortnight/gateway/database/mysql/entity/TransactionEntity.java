package com.fortnight.gateway.database.mysql.entity;

import com.fortnight.domain.TransactionType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity(name = "transactions")
public class TransactionEntity {

    @Id
    private String correlation;
    private BigDecimal amount;
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;
    private Instant date;

    @ManyToOne()
    @JoinColumn(name = "document")
    private BalanceEntity balance;
}
