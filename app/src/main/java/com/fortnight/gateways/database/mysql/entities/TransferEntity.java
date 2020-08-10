package com.fortnight.gateways.database.mysql.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "transfers")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "transaction", referencedColumnName = "correlation")
    private TransactionEntity transaction;

    @ManyToOne
    @JoinColumn(name = "debtor", referencedColumnName = "document")
    private CustomerEntity debtor;

    @ManyToOne
    @JoinColumn(name = "creditor", referencedColumnName = "document")
    private CustomerEntity creditor;
}
