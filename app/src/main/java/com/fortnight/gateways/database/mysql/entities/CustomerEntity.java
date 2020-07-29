package com.fortnight.gateways.database.mysql.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity(name = "customers")
public class CustomerEntity {

    @Id
    private String document;
    private String name;
    private BigDecimal balance = BigDecimal.ZERO;
    private Instant creation;
}
