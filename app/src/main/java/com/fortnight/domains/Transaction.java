package com.fortnight.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Transaction {

    private Customer customer;
    private String correlation;
    private BigDecimal amount;
    private TransactionType type;
    private Transfer transfer;
    private Instant date;

}
