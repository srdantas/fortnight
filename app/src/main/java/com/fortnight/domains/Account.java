package com.fortnight.domains;

import lombok.Data;

import java.time.Instant;

@Data
public class Account {

    private String document;
    private String name;
    private Instant creation;
    private Balance balance;
}
