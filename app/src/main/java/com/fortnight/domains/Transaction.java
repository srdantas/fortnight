package com.fortnight.domains;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Transaction {

    private String document;
    private String correlation;
    private BigDecimal amount;
    private TransactionType type;
    private Transfer transfer;
    private Instant date;

}
