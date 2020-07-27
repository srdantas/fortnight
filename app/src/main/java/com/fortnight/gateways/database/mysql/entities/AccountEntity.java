package com.fortnight.gateways.database.mysql.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity(name = "accounts")
public class AccountEntity {

    @Id
    private String document;
    private String name;
    private Instant creation;

    @OneToOne()
    @JoinColumn(name = "document")
    private BalanceEntity balance;
}
