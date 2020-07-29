package com.fortnight.domains;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Customer {

    private String document;
    private String name;
    private Instant creation;
    private BigDecimal balance;
}
