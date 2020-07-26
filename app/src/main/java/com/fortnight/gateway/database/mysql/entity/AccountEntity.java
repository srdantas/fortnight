package com.fortnight.gateway.database.mysql.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.Instant;

@Data
@Entity(name = "accounts")
public class AccountEntity {

    @Id
    private String document;
    private String name;
    private Instant creation;

    @OneToOne
    @JoinColumn(name = "document")
    private BalanceEntity balance;
}
