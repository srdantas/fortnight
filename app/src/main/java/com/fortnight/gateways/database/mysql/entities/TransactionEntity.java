package com.fortnight.gateways.database.mysql.entities;

import com.fortnight.domains.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correlation;
    private BigDecimal amount;
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "document")
    private CustomerEntity customer;

}
