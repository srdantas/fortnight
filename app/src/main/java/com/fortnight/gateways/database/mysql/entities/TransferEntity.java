package com.fortnight.gateways.database.mysql.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "transfers")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "document")
    private CustomerEntity debtor;

    @ManyToOne
    @JoinColumn(referencedColumnName = "document")
    private CustomerEntity creditor;
}
